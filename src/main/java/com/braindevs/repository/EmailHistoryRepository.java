package com.braindevs.repository;


import com.braindevs.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Long> {
    Optional<EmailHistoryEntity> findTop1ByEmailOrderByCreatedDateDesc(String email);
}
