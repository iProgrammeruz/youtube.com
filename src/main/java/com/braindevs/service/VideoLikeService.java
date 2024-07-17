package com.braindevs.service;

import com.braindevs.dto.channel.ChannelDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.dto.video.VideoLikeCreateDto;
import com.braindevs.dto.video.VideoLikeDto;
import com.braindevs.entity.VideoLikeEntity;
import com.braindevs.enums.EmotionStatus;
import com.braindevs.mapper.VideoLikeMapper;
import com.braindevs.repository.VideoLikeRepository;
import com.braindevs.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoLikeService {
    private final VideoLikeRepository videoLikeRepository;

    public void reaction(VideoLikeCreateDto dto) {
        Long profileId = SecurityUtil.getProfileId();
        Optional<VideoLikeEntity> optional = videoLikeRepository.findByVideoIdAndProfileId(dto.getVideoId(), profileId);

        if (optional.isEmpty()) {
            VideoLikeEntity entity = new VideoLikeEntity();
            entity.setVideoId(dto.getVideoId());
            entity.setProfileId(profileId);
            entity.setEmotionStatus(dto.getReaction());
            videoLikeRepository.save(entity);
            return;
        }

        VideoLikeEntity entity = optional.get();
        if (entity.getEmotionStatus().equals(dto.getReaction())) {
            videoLikeRepository.delete(entity);
            return;
        }

        entity.setEmotionStatus(dto.getReaction());
        videoLikeRepository.save(entity);
    }

    public Page<VideoLikeDto> getByUserId(Long profileId, EmotionStatus reaction, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate"));
        Page<VideoLikeMapper> pageEntity = videoLikeRepository.findAllByProfileIdAndReaction(profileId, reaction, pageable);
        List<VideoLikeDto> list = pageEntity.getContent()
                .stream()
                .map(entity ->{
                    // create chanel
                    ChannelDto chanelDto = new ChannelDto();
                    chanelDto.setId(entity.getChanelId());
                    chanelDto.setName(entity.getChanelName());

                    //create video
                    VideoDto videoDto = new VideoDto();
                    videoDto.setId(entity.getVideoId());
                    videoDto.setTitle(entity.getVideoTitle());
                    videoDto.setChannel(chanelDto);
                    videoDto.setPreviewAttachId(entity.getPreviewAttachId());

                    VideoLikeDto dto = new VideoLikeDto();
                    dto.setId(entity.getId());
                    dto.setVideo(videoDto);
                    return dto;
                })
                .toList();

        long totalElements = pageEntity.getTotalElements();
        return new PageImpl<>(list, pageable, totalElements);
    }
}
