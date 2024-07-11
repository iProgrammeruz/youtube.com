package com.braindevs.service;

import com.braindevs.dto.tag.TagDto;
import com.braindevs.dto.tag.VideoTagCreateDto;
import com.braindevs.dto.tag.VideoTagDto;
import com.braindevs.entity.VideoTagEntity;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.VideoTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoTagService {
    private final VideoTagRepository videoTagRepository;

    public String create(VideoTagCreateDto dto) {
        var entity = VideoTagEntity.builder()
                .videoId(dto.getVideoId())
                .tagId(dto.getTagId())
                .build();

        videoTagRepository.save(entity);
        return entity.getId();
    }

    public String delete(String videoId, String tagId) {
        int effectedRows = videoTagRepository.deleteByVideoIdAndTagId(videoId, tagId);
        if (effectedRows == 0) {
            throw new AppBadException("delete failed");
        }
        return "successfully deleted";
    }

    public List<VideoTagDto> getTagByVideoId(String videoId) {
        return videoTagRepository.findAllByVideoId(videoId)
                .stream()
                .map(entity -> {
                    VideoTagDto dto = new VideoTagDto();
                    dto.setId(entity.getId());
                    dto.setVideoId(entity.getVideoId());
                    dto.setCreatedDate(entity.getTagCreatedDate());
                    // create tag
                    TagDto tag = new TagDto();
                    tag.setId(entity.getTagId());
                    tag.setName(entity.getTagName());
                    dto.setTag(tag);
                    return dto;
                })
                .toList();
    }
}
