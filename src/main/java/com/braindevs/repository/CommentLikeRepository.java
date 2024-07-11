package com.braindevs.repository;

import com.braindevs.entity.CommentLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, String> {

    Optional<CommentLikeEntity> findByCommentIdAndProfileId(String commentId, Long profileId);

    Page<CommentLikeEntity> findAllByProfileId(Long profileId, Pageable pageable);
}
