package com.braindevs.repository;

import com.braindevs.entity.VideoLikeEntity;
import com.braindevs.enums.EmotionStatus;
import com.braindevs.enums.ReportType;
import com.braindevs.mapper.VideoLikeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, String> {

    Optional<VideoLikeEntity> findByVideoIdAndProfileId(String videoId, Long profileId);

    @Query( " SELECT " +
            " vl.id AS id, " +
            " v.id AS videoId," +
            " v.title AS videoTitle," +
            " v.previewAttachId AS previewAttachId, " +
            " ch.id AS chanelId," +
            " ch.name AS chanelName " +
            " FROM VideoLikeEntity AS vl " +
            " INNER JOIN vl.video AS v " +
            " INNER JOIN v.channel AS ch " +
            " WHERE vl.profileId = ?1 " +
            " AND vl.emotionStatus = ?2 ")
    Page<VideoLikeMapper> findAllByProfileIdAndReaction(Long profileId, EmotionStatus status, Pageable pageable);
}
