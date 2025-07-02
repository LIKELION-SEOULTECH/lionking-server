package com.example.lionking.domain.email.service;

import com.example.lionking.domain.email.entity.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkEmailService {
    private final JavaMailSender mailSender;
    private final EmailNotifyService emailNotifyService;

/*  @Value("${app.email.from}")
    private String fromEmail;*/

    private static final String fromEmail = "choikang7157@gmail.com";

    @Async("emailTaskExecutor")
    public CompletableFuture<Void> sendBulkNotifications() {
        List<Email> targets = emailNotifyService.getPendingEmails();

        if (targets.isEmpty()) {
            System.out.println("발송할 이메일이 없습니다.");
            return CompletableFuture.completedFuture(null);
        }

        System.out.println("총 " + targets.size() + "개 이메일 발송 시작");

        List<List<Email>> batches = createBatches(targets, 50);

        for (int i = 0; i < batches.size(); i++) {
            List<Email> batch = batches.get(i);
            sendBatchEmails(batch);

            if (i < batches.size() - 1) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        System.out.println("모든 이메일 발송 완료");
        return CompletableFuture.completedFuture(null);
    }

    private void sendBatchEmails(List<Email> batch) {
        MimeMessage[] messages = new MimeMessage[batch.size()];

        for (int i = 0; i < batch.size(); i++) {
            Email email = batch.get(i);
            messages[i] = createMimeMessage(email.getEmail());
        }

        try {
            mailSender.send(messages);
            emailNotifyService.markAsNotified(batch);
        } catch (Exception e) {
            System.err.println("배치 발송 실패: " + e.getMessage());
        }
    }

    private MimeMessage createMimeMessage(String to) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("🎉 모집이 시작되었습니다!");

            String htmlContent = """
                <html>
                <body>
                    <h2>안녕하세요!</h2>
                    <p>기다리시던 모집이 드디어 시작되었습니다.</p>
                    <p>지금 바로 지원하기</p>
                    <p>감사합니다.</p>
                </body>
                </html>
                """;

            helper.setText(htmlContent, true);
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException("메시지 생성 실패", e);
        }
    }

    private <T> List<List<T>> createBatches(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        int totalSize = list.size();

        for (int i = 0; i < totalSize; i += batchSize) {
            int end = Math.min(i + batchSize, totalSize);
            batches.add(list.subList(i, end));
        }

        return batches;
    }
}
