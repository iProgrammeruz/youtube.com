package com.braindevs.repository;

import com.braindevs.entity.ChanelEntity;
import com.braindevs.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;


public interface ChanelRepository extends CrudRepository<ChanelEntity,String> {
    @Transactional
    @Modifying
    @Query("update ChanelEntity set photoId = ?1 where id = ?2")
    void updatePhotoId(String photoId ,String chanelId);

    @Transactional
    @Modifying
    @Query("update ChanelEntity set bannerId = ?1 where id = ?2")
    void updateBannerId(String bannerId ,String chanelId);

    @Transactional
    @Modifying
    @Query("update ChanelEntity set status = ?1 where id = ?2")
    void updateStatus(Status status , String chanelId);

    Page<ChanelEntity> findAllBy(Pageable pageable);

    List<ChanelEntity> findAllByProfileId(Long profileId);

    Optional<ChanelEntity> findByProfileId(Long profileId);

}
