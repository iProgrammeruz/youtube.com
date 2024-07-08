package com.braindevs.repository;

import com.braindevs.entity.ChannelEntity;
import com.braindevs.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;


public interface ChannelRepository extends CrudRepository<ChannelEntity,String> {
    @Transactional
    @Modifying
    @Query("update ChannelEntity set photoId = ?1 where id = ?2")
    void updatePhotoId(String photoId ,String chanelId);

    @Transactional
    @Modifying
    @Query("update ChannelEntity set bannerId = ?1 where id = ?2")
    void updateBannerId(String bannerId ,String chanelId);

    @Transactional
    @Modifying
    @Query("update ChannelEntity set status = ?1 where id = ?2")
    void updateStatus(Status status , String chanelId);

    Page<ChannelEntity> findAllBy(Pageable pageable);

    List<ChannelEntity> findAllByProfileId(Long profileId);

    Optional<ChannelEntity> findByProfileId(Long profileId);

}
