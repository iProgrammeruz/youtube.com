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



    public String create(VideoCreateDto video) {
        VideoEntity entity = new VideoEntity();
        entity.setTitle(video.getTitle());
        entity.setDescription(video.getDescription());
        entity.setStatus(video.getStatus());
        entity.setType(video.getType());
        entity.setAttachId(attachService.get(video.getAttachId()).getId());
        entity.setPreviewAttachId(attachService.get(video.getPreviewAttachId()).getId());
        entity.setCategoryId(categoryService.findById(video.getCategoryId()).getId());
        entity.setCreatedDate(LocalDateTime.now());
        //channelId


    }


    public void increaseShareCount(String videoId) {
        videoRepository.increaseShareCount(videoId);
    }


}
