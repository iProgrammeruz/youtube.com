package com.braindevs.repository;

import com.braindevs.entity.VideoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<String, VideoEntity> {
















    @Transactional
    @Modifying
    @Query("update VideoEntity set sharedCount = coalesce(viewCount, 0) +1 where id = ?1")
    void increaseShareCount(String videoId);


}
