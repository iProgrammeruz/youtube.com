package com.braindevs.repository;

import com.braindevs.entity.ChannelEntity;
import com.braindevs.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Statement;
import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String> {

    Optional<ChannelEntity> findByName(String name);
    Optional<ChannelEntity> findByOwnerIdAndId(Long owner ,String channelId);

    Page<ChannelEntity> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ChannelEntity set status =?2 where id = ?1")
    void updateStatus(String channelId, Status status);

    Page<ChannelEntity> findAllByOwnerId(Long ownerId, Pageable pageable);
}
