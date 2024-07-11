package com.braindevs.service;

import com.braindevs.dto.comment.CommentCreateDto;
import com.braindevs.dto.comment.CommentDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.entity.CommentEntity;
import com.braindevs.entity.ProfileEntity;
import com.braindevs.enums.ProfileRole;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.CommentRepository;
import com.braindevs.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public String create(CommentCreateDto dto) {
        var entity = CommentEntity.builder()
                .videoId(dto.getVideoId())
                .content(dto.getContent())
                .replyId(dto.getReplId())
                .profileId(SecurityUtil.getProfileId())
                .build();

        commentRepository.save(entity);
        return entity.getId();
    }

    public CommentDto update(String commentId, String content) {
        Long profileId = SecurityUtil.getProfileId();
        CommentEntity entity = commentRepository.findByIdAndProfileId(commentId, profileId)
                        .orElseThrow(()-> new AppBadException("No access to this comment"));

        entity.setContent(content == null ? entity.getContent() : content);
        commentRepository.save(entity);

        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .build();
    }

    public void delete(String commentId) {
        isOwnerOrAdmin(commentId);
        commentRepository.deleteById(commentId);
    }

    public Page<CommentDto> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<CommentEntity> pageEntity = commentRepository.findAllBy(pageable);
        return mapEntityPageToDto(pageEntity,pageable,this::toDto);
    }

    public Page<CommentDto> getAllByUserId(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<CommentEntity> pageEntity = commentRepository.findAllByProfileId(userId, pageable);
        return mapEntityPageToDto(pageEntity,pageable,this::toDto);
    }

    public Page<CommentDto> getAllByVideoId(String videoId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<CommentEntity> pageEntity = commentRepository.findAllByVideoId(videoId, pageable);
        return mapEntityPageToDto(pageEntity,pageable,this::commentInfo);
    }

    public Page<CommentDto> getAllReplyByCommentId(String commentId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<CommentEntity> pageEntity = commentRepository.findAllByReplyId(commentId, pageable);
        return mapEntityPageToDto(pageEntity,pageable,this::commentInfo);
    }

    public Page<CommentDto> mapEntityPageToDto(Page<CommentEntity> pageEntity,
                                               Pageable pageable,
                                               Function<CommentEntity, CommentDto> converter) {
        List<CommentDto> list = pageEntity.getContent()
                .stream()
                .map(converter)
                .toList();

        long totalElements = pageEntity.getTotalElements();
        return new PageImpl<>(list, pageable, totalElements);
    }

    private CommentDto toDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setDislikeCount(entity.getDislikeCount());
        dto.setLikeCount(entity.getLikeCount());
        dto.setCreatedDate(entity.getCreatedDate());

        // create video
        VideoDto video = new VideoDto();
        video.setId(entity.getVideoId());
        video.setTitle(entity.getVideo().getTitle());
        video.setPreviewAttachId(entity.getVideo().getPreviewAttachId());
        dto.setVideo(video);

        return dto;
    }

    private CommentDto commentInfo(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setDislikeCount(entity.getDislikeCount());
        dto.setLikeCount(entity.getLikeCount());
        dto.setCreatedDate(entity.getCreatedDate());

        // create profile
        ProfileDto profile = new ProfileDto();
        profile.setId(entity.getProfileId());
        profile.setName(entity.getProfile().getName());
        profile.setSurname(entity.getProfile().getSurname());
        profile.setSurname(entity.getProfile().getSurname());
        profile.setPhotoId(entity.getProfile().getPhotoId());
        dto.setProfile(profile);

        return dto;
    }

    public CommentEntity get(String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new AppBadException("Comment not found"));
    }

    private void isOwnerOrAdmin(String commentId) {
        ProfileEntity currentUser = SecurityUtil.getProfile();
        CommentEntity comment = get(commentId);
        if (!comment.getProfileId().equals(currentUser.getId())
                && currentUser.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppBadException("No access to this comment");
        }
    }
}
