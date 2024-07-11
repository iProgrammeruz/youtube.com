package com.braindevs.service;

import com.braindevs.dto.video.VideoShortInfoDto;
import com.braindevs.dto.channel.ChannelShortInfoDto;

import com.braindevs.dto.video.VideoCreateDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.dto.video.VideoUpdateDto;
import com.braindevs.entity.VideoEntity;
import com.braindevs.enums.VideoStatus;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.VideoRepository;
import com.braindevs.util.MapperUtil;
import com.braindevs.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VideoService {


    private final VideoRepository videoRepository;
    private final AttachService attachService;
    private final CategoryService categoryService;
    private final ChannelService channelService;
    private final ViewCountService viewCountService;


    //1. Create video
    public String create(VideoCreateDto video) {
        VideoEntity saved = videoRepository.save(toEntity(video));
        return saved.getId();
    }


    //2. Update video detail
    public String update(VideoUpdateDto videoUpdateDto, String videoId) {
        isOwner(videoId);
        VideoEntity entity = get(videoId);
        entity.setTitle(videoUpdateDto.getTitle());
        entity.setDescription(videoUpdateDto.getDescription());
        entity.setType(videoUpdateDto.getType());
        entity.setAttachId(attachService.get(videoUpdateDto.getAttachId()).getId());
        entity.setPreviewAttachId(attachService.get(videoUpdateDto.getPreviewAttachId()).getId());
        entity.setCategoryId(categoryService.findById(videoUpdateDto.getCategoryId()).getId());
        videoRepository.save(entity);
        return "Successfully updated video!";
    }


    //3. update video status
    public String updateStatus(String videoId, VideoStatus videoStatus) {
        isOwner(videoId);
        videoRepository.updateStatus(videoId, videoStatus);
        return "Successfully updated video status!";
    }


    //4. Increase view count
    public void increaseViewCount(String videoId, Long profileId) {
        videoRepository.increaseViewCount(videoId);
        Integer count = videoRepository.getViewCount(videoId);
        viewCountService.create(videoId, count, profileId);
    }


    //5. Get videos by category ID with pagination
    public PageImpl<VideoShortInfoDto> getVideosByCategoryId(Integer categoryId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<VideoEntity> entityPage = videoRepository.findAllByCategoryId(categoryId, pageable);
        List<VideoShortInfoDto> shortInfoDtoList = entityPage.getContent()
                .stream()
                .map(this::toDtoShortInfo)
                .toList();
        return new PageImpl<>(shortInfoDtoList, pageable, entityPage.getTotalElements());
    }


    //6. Get videos by title with pagination
    public PageImpl<VideoShortInfoDto> getVideosByTitle(String title, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<VideoEntity> entityPage = videoRepository.findAllByTitle(title, pageable);
        List<VideoShortInfoDto> shortInfoDtoList = entityPage.getContent()
                .stream()
                .map(this::toDtoShortInfo)
                .toList();
        return new PageImpl<>(shortInfoDtoList, pageable, entityPage.getTotalElements());
    }


    //7. Get videos by tagId with pagination
    public PageImpl<VideoShortInfoDto> getVideosByTagId(String tagId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Object[]> vshidto = videoRepository.findAllByVideoTagId(tagId, pageable);
        List<VideoShortInfoDto> shortInfoDtoList = new ArrayList<>();
        for (Object[] objects : vshidto) {
            VideoShortInfoDto dto = new VideoShortInfoDto();
            dto.setId(MapperUtil.getStringValue(objects[0]));
            dto.setTitle(MapperUtil.getStringValue(objects[1]));
            dto.setPreviewAttach(attachService.toDto(MapperUtil.getStringValue(objects[2])));
            dto.setViewCount(MapperUtil.getIntegerValue(objects[3]));
            dto.setChannel(new ChannelShortInfoDto(
                    MapperUtil.getStringValue(objects[4]),
                    MapperUtil.getStringValue(objects[5]),
                    attachService.asUrlString(MapperUtil.getStringValue(objects[6]))));
            shortInfoDtoList.add(dto);

        }
        return new PageImpl<>(shortInfoDtoList, pageable, vshidto.getTotalElements());
    }


    //
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
        entity.setChannelId(channelService.get(video.getChannelId()).getId());
        return entity;
    }


    //Video short info
    public VideoShortInfoDto toDtoShortInfo(VideoEntity entity) {
        VideoShortInfoDto vshid = new VideoShortInfoDto();
        vshid.setId(entity.getId());
        vshid.setTitle(entity.getTitle());
        vshid.setPreviewAttach(attachService.toDto(entity.getPreviewAttachId()));
        vshid.setPublishedDate(entity.getPublishedDate());
        vshid.setChannel(channelService.getChanelByIdShort(entity.getChannelId()));
        vshid.setViewCount(entity.getViewCount());
        return vshid;
    }


    public VideoDto toDto(VideoEntity entity) {
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





   /* private void isAdminOrOwner(String videoId) {
        ProfileEntity profile = SecurityUtil.getProfile();
        PlayListEntity playListEntity = getById(playlistId);
        if (!playListEntity.getChanel().getProfileId().equals(profile.getId())
                || !profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppBadException("You dont have permission to delete this playlist");
        }
    }*/

    private void isOwner(String videoId) {
        Long profileId = SecurityUtil.getProfileId();
        VideoEntity videoEntity = get(videoId);
        if (!videoEntity.getChannel().getProfileId().equals(profileId)) {
            throw new AppBadException("You dont have permission to update this video");
        }
    }


    public VideoEntity get(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new AppBadException("Video not found!"));
    }


}
