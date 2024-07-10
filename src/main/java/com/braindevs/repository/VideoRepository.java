package com.braindevs.repository;

import com.braindevs.dto.video.VideoShortInfoDto;
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


    Page<VideoEntity> findAllByCategoryId(Integer  categoryId, Pageable pageable);


    Page<VideoEntity> findAllByTitle(String title, Pageable pageable);


    //@Query( " SELECT v.id, v.title, v.previewAttachId, v.publishedDate, v.viewCount, ch.id, ch.name" +
    ////            " FROM VideoEntity AS v " +
    ////            " INNER JOIN v.chanel AS ch " +
    ////            " WHERE v. = ?1 ")
    ////    Page<VideoShortInfoMapper> findByTagId(String title, Pageable pageable);


//    @Query(value = "SELECT " +
//            "v.id," +
//            "v.title, " +
//            "v.previewAttachId as photoId, " +
//            "json_build_object(" +
//            "'id', c.id, " +
//            "'name', c.name," +
//            "'photoId',c.photoId" +
//            ") as channel," +
//            "v.publishedDate," +
//            "v.viewCount " +
//            "from VideoEntity as v " +
//            "inner join AttachEntity as a on a.id = VideoEntity .attachId " +
//            "inner join ChannelEntity as c on c.id = v.channelId " +
//            "inner join VideoTagEntity as vt on vt.id = v.id " +
//            "where vt.tagId = :tagId ")
//    Page<VideoShortInfoDto> findAllByVideoTagId(String tagId, Pageable pageable);


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
