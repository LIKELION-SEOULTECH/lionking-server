package com.example.lionking.domain.email.service;

import com.example.lionking.domain.email.dto.request.EmailRequest;
import com.example.lionking.domain.email.entity.Email;
import com.example.lionking.domain.email.repository.EmailNotifyRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotifyService {
    private final EmailNotifyRepository emailNotifyRepository;

    @Transactional
    public void registerEmail(EmailRequest req) {
        if(emailNotifyRepository.existsByEmail(req.getEmail())) {
            throw new CustomException(GlobalErrorCode.INVALID_PERMISSION,"이미 등록된 이메일 입니다");
        }
        Email email = req.toNotifyEmail(req);
        emailNotifyRepository.save(email);
    }

    @Transactional(readOnly = true)
    public List<Email> getPendingEmails() {
        return emailNotifyRepository.findByNotifiedFalse();
    }

    @Transactional
    public void markAsNotified(List<Email> emails) {
        emails.forEach(email -> email.setNotified(true));
        emailNotifyRepository.saveAll(emails);
    }
}
