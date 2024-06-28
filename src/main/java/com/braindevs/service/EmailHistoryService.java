package com.braindevs.service;

import com.braindevs.entity.EmailHistoryEntity;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.EmailHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmailHistoryService {
    private final EmailHistoryRepository emailHistoryRepository;


    public void save(String email, String message) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(message);
        emailHistoryRepository.save(entity);
    }

    public void isNotExpiredEmail(String email) {
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findTop1ByEmailOrderByCreatedDateDesc(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email history not found");
        }
        EmailHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new AppBadException("Confirmation time expired");
        }
    }
}
