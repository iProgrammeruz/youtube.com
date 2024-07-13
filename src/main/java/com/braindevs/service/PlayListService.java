package com.braindevs.service;

import com.braindevs.dto.attach.AttachDto;
import com.braindevs.dto.channel.ChannelDto;
import com.braindevs.dto.playList.PlayListCreateDto;
import com.braindevs.dto.playList.PlayListDto;
import com.braindevs.dto.playList.PlayListUpdateDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.entity.PlayListEntity;
import com.braindevs.entity.ProfileEntity;
import com.braindevs.enums.PlayListStatus;
import com.braindevs.enums.ProfileRole;
import com.braindevs.exp.AppBadException;
import com.braindevs.mapper.PlayListMapper;
import com.braindevs.mapper.PlaylistFullInfoMapper;
import com.braindevs.repository.PlaylistRepository;
import com.braindevs.util.SecurityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PlayListService {
    private final PlaylistRepository playlistRepository;
    private final AttachService attachService;

    public long create(PlayListCreateDto dto) {
        var entity = PlayListEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .chanelId(dto.getChanelId())
                .orderNumber(dto.getOrderNumber())
                .build();

        playlistRepository.save(entity);
        return entity.getId();
    }

    public PlayListDto update(Long playlistId, PlayListUpdateDto dto) {
        isOwner(playlistId);
        PlayListEntity entity = getById(playlistId);
        entity.setName(dto.getName() == null ? entity.getName() : dto.getName());
        entity.setDescription(dto.getDescription() == null ? entity.getDescription() : dto.getDescription());
        entity.setOrderNumber(dto.getOrderNumber() == null ? entity.getOrderNumber() : dto.getOrderNumber());
        playlistRepository.save(entity);

        return PlayListDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .orderNumber(entity.getOrderNumber())
                .status(entity.getStatus())
                .chanelId(entity.getChanelId())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public void updateStatus(Long playlistId, PlayListStatus status) {
        isOwner(playlistId);
        playlistRepository.updateStatus(playlistId, status);
    }

    public void delete(Long playlistId) {
        isAdminOrOwner(playlistId);
        playlistRepository.deleteById(playlistId);
    }

    public Page<PlayListDto> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PlaylistFullInfoMapper> entityPage = playlistRepository.findAllBy(pageable);
        List<PlayListDto> list = entityPage.getContent()
                .stream()
                .map(this::toFullInfo)
                .toList();
        long totalElements = entityPage.getTotalElements();
        return new PageImpl<>(list, pageable, totalElements);
    }

    public List<PlayListDto> getByUserId(Long userId) {
        return playlistRepository.getByUserId(userId)
                .stream()
                .map(this::toFullInfo)
                .toList();
    }

    public List<PlayListDto> getByChanelId(String chanelId) {
        List<Object[]> resultList = playlistRepository.findAllByChanelId(chanelId);
        return resultList
                .stream()
                .map(objects -> {
                    PlayListDto dto = new PlayListDto();
                    dto.setId((Long) objects[0]);
                    dto.setName((String) objects[1]);
                    dto.setCreatedDate((LocalDateTime) objects[2]);
                    dto.setVideoCount((Long) objects[5]);

                    ObjectMapper objectMapper = new ObjectMapper();
                    List<VideoDto> videoList = null;
                    try {
                        videoList = objectMapper.readValue(objects[6].toString(), new TypeReference<List<VideoDto>>() {});
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    dto.setVideoList(videoList);

                    // create chanel
                    ChannelDto chanelDto = new ChannelDto();
                    chanelDto.setId((String) objects[3]);
                    chanelDto.setName((String) objects[4]);
                    dto.setChanel(chanelDto);

                    return dto;
                })
                .toList();
    }

    public PlayListDto getByPlaylistId(Long playlistId) {
        PlayListMapper mapper = playlistRepository.getById(playlistId);
        return PlayListDto.builder()
                .id(mapper.getId())
                .name(mapper.getName())
                .videoCount(mapper.getVideoCount())
                .totalViewCount(mapper.getTotalViewCount())
                .build();
    }

    private PlayListDto toFullInfo(PlaylistFullInfoMapper entity) {
        // create playlist
        PlayListDto dto = new PlayListDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());

        // create channel
        ChannelDto chanel = new ChannelDto();
        chanel.setId(entity.getChanelId());
        chanel.setName(entity.getChanelName());
        chanel.setPhotoId(entity.getChanelPhotoId());
        dto.setChanel(chanel);

        // create profile
        ProfileDto profile = new ProfileDto();
        profile.setId(entity.getProfileId());
        profile.setName(entity.getProfileName());
        profile.setSurname(entity.getProfileSurname());
        profile.setPhotoId(entity.getProfilePhotoId());
        dto.setProfile(profile);
        return dto;
    }

    private void isAdminOrOwner(Long playlistId) {
        ProfileEntity profile = SecurityUtil.getProfile();
        PlayListEntity playListEntity = getById(playlistId);
        if (playListEntity.getChanel().getProfileId() != profile.getId()
                || !profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppBadException("You dont have permission to delete this playlist");
        }
    }

    private void isOwner(Long playlistId) {
        Long profileId = SecurityUtil.getProfileId();
        PlayListEntity playListEntity = getById(playlistId);
        if (playListEntity.getChanel().getProfileId() != profileId) {
            throw new AppBadException("You dont have permission to update this playlist");
        }
    }

    public PlayListEntity getById(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new AppBadException("Playlist not found"));
    }
}
