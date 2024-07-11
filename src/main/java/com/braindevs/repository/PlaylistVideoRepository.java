package com.braindevs.repository;

import com.braindevs.entity.PlaylistVideoEntity;
import com.braindevs.mapper.PlaylistVideoInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistVideoRepository extends CrudRepository<PlaylistVideoEntity, String> {

    @Transactional
    @Modifying
    int deleteByPlaylistIdAndVideoId(Long playlistId, String videoId);

    @Query( " SELECT p.playlistId AS playlistId," +
            " p.createdDate AS createdDate," +
            " p.orderNumber AS orderNumber, " +
            " v.id AS videoId," +
            " v.title AS videoTitle," +
            " v.previewAttachId AS previewAttachId, " +
            " ch.id AS chanelId" +
            ", ch.name AS chanelName " +
            " FROM PlaylistVideoEntity AS p" +
            " INNER JOIN p.video AS v " +
            " INNER JOIN v.channel AS ch " +
            " WHERE p.playlistId = ?1 ")
    Page<PlaylistVideoInfoMapper> findAllByPlaylistId(Long playlistId, Pageable pageable);
}
