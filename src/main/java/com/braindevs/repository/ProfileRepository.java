package com.braindevs.repository;
import com.braindevs.entity.ProfileEntity;
import com.braindevs.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);
    Optional<ProfileEntity> findById(Long profileId);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id =?1")
    void updateStatus(Long profileId, ProfileStatus status);

    Optional<ProfileEntity> findByEmailAndPasswordAndVisibleTrue(String email, String password);
}
