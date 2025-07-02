package com.example.lionking.domain.email.service;

import com.example.lionking.domain.email.entity.Email;
import com.example.lionking.domain.email.repository.EmailNotifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotifyScheduler {
    private final BulkEmailService bulkEmailService;
    private final EmailNotifyRepository repository;

/*    @Value("${app.notify.start-date}")
    private String notifyStartDate;*/

    // 정적 상수로 날짜 고정
    private static final String notifyStartDate = "2026-01-01";

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시
    public void checkAndSendNotifications() {
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.parse(notifyStartDate);

        if (now.isEqual(start)) {
            // DB에서 아직 발송되지 않은 이메일이 있는지 체크
            List<Email> pendingEmails = repository.findByNotifiedFalse();

            if (!pendingEmails.isEmpty()) {
                System.out.println("모집 알림 발송 시작: " + now);
                System.out.println("발송 대상: " + pendingEmails.size() + "명");

                bulkEmailService.sendBulkNotifications()
                        .thenRun(() -> System.out.println("모든 알림 발송 완료"));
            } else {
                System.out.println("발송할 이메일이 없습니다. (이미 발송 완료)");
            }
        }
    }
}
