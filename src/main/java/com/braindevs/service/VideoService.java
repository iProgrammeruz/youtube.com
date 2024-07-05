package com.braindevs.service;

import com.braindevs.dto.video.VideoCreateDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.entity.AttachEntity;
import com.braindevs.entity.CategoryEntity;
import com.braindevs.entity.VideoEntity;
import com.braindevs.repository.AttachRepository;
import com.braindevs.repository.CategoryRepository;
import com.braindevs.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VideoService {


    private final VideoRepository videoRepository;
    private final AttachService attachService;
    private final CategoryService categoryService;
    private final ChanelService chanelService;



    public VideoDto create(VideoCreateDto video) {
        VideoEntity entity = toEntity(video);
        VideoEntity saved = videoRepository.save(entity);
        return toDto(saved);
    }




    public void increaseShareCount(String videoId) {
        videoRepository.increaseShareCount(videoId);
    }

    public VideoEntity toEntity(VideoCreateDto video) {
        VideoEntity entity = new VideoEntity();
        entity.setTitle(video.getTitle());
        entity.setDescription(video.getDescription());
        entity.setStatus(video.getStatus());
        entity.setType(video.getType());
        entity.setAttachId(attachService.get(video.getAttachId()).getId());
        entity.setPreviewAttachId(attachService.get(video.getPreviewAttachId()).getId());
        entity.setCategoryId(categoryService.findById(video.getCategoryId()).getId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setChannelId(chanelService.get(video.getChannelId()).getId());
        return entity;
    }

    private VideoDto toDto(VideoEntity entity) {
        VideoDto dto = new VideoDto();
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setAttachId(entity.getAttachId());
        dto.setPreviewAttachId(entity.getPreviewAttachId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setChannelId(entity.getChannelId());
        return dto;
    }


}
