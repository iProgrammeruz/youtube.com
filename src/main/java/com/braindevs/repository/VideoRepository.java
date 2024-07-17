package com.braindevs.repository;

import com.braindevs.entity.VideoEntity;
import com.braindevs.enums.VideoStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface VideoRepository extends CrudRepository<VideoEntity, String>,
        PagingAndSortingRepository<VideoEntity, String> {

    @Transactional
    @Modifying
    @Query("update VideoEntity set status = ?2 where id = ?1 ")
    void updateStatus(String videoId, VideoStatus videoStatus);


    Page<VideoEntity> findAllByCategoryId(Integer categoryId, Pageable pageable);


    Page<VideoEntity> findAllByTitle(String title, Pageable pageable);

    @Query("select " +
            "v.id," +
            "v.title, " +
            "v.previewAttachId , " +
            "v.publishedDate," +
            "v.viewCount," +
            "c.id ," +
            "c.name ," +
            "c.photoId " +
            "from VideoEntity as v " +
            "inner join v.attach  as a " +
            "inner join v.channel  as c " +
            "inner join VideoTagEntity as vt on vt.id = v.id " +
            "where vt.tagId = ?1 ")
    Page<Object[]> findAllByVideoTagId(String tagId, Pageable pageable);


    @Transactional
    @Modifying
    @Query("update VideoEntity set sharedCount = coalesce(viewCount, 0) +1 where id = ?1")
    void increaseShareCount(String videoId);


    @Transactional
    @Modifying
    @Query("update VideoEntity set viewCount = coalesce(viewCount, 0) +1 where id = ?1")
    void increaseViewCount(String videoId);


    @Query("select v.viewCount from VideoEntity v where v.id = ?1")
    Integer getViewCount(String videoId);


}
