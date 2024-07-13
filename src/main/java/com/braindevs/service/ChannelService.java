package com.braindevs.service;

import com.braindevs.dto.channel.ChannelCreateDto;
import com.braindevs.dto.channel.ChannelDto;
import com.braindevs.dto.channel.ChannelShortInfoDto;
import com.braindevs.dto.channel.ChannelUpdateDto;
import com.braindevs.entity.ChannelEntity;
import com.braindevs.entity.ProfileEntity;
import com.braindevs.enums.ProfileRole;
import com.braindevs.enums.Status;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.ChannelRepository;
import com.braindevs.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final AttachService attachService;

    public ChannelDto create(ChannelCreateDto dto) {
        ChannelEntity entity = toEntity(dto);
        ChannelEntity saved = channelRepository.save(entity);
        return toDto(saved);
    }

    public ChannelDto update(String chanelId, ChannelUpdateDto dto) {
        isOwner(chanelId);     // check current user is OWNER this channel

        ChannelEntity channelEntity = get(chanelId);
        if (dto.getName() != null) {
            channelEntity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            channelEntity.setDescription(dto.getDescription());
        }
        ChannelEntity saved = channelRepository.save(channelEntity);
        return toDto(saved);
    }

    public void updatePhoto(String chanelId, String newPhotoId) {
        isOwner(chanelId);      // check current user is OWNER this channel

        ChannelEntity channelEntity = get(chanelId);
        String oldPhotoId = channelEntity.getPhotoId();
        channelRepository.updatePhotoId(newPhotoId, chanelId);

        if (oldPhotoId != null) {
            attachService.delete(oldPhotoId);
        }
    }

    public void updateBanner(String chanelId, String newBannerId) {
        isOwner(chanelId);      // check current user is OWNER this channel

        ChannelEntity channelEntity = get(chanelId);
        String oldBannerId = channelEntity.getBannerId();
        channelRepository.updateBannerId(newBannerId, chanelId);

        if (oldBannerId != null) {
            attachService.delete(oldBannerId);
        }
    }

    public Page<ChannelDto> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ChannelEntity> entityPage = channelRepository.findAllBy(pageable);
        List<ChannelDto> list = entityPage.getContent()
                .stream()
                .map(this::toDto)
                .toList();
        long totalElements = entityPage.getTotalElements();
        return new PageImpl<>(list, pageable, totalElements);
    }

    public void changeStatus(String chanelId, Status status) {
        isAdminOrOwner(chanelId);       // check current user is OWNER this channel or ADMIN
        channelRepository.updateStatus(status, chanelId);
    }

    public List<ChannelDto> getUserChanels() {
        Long profileId = SecurityUtil.getProfileId();
        return channelRepository.findAllByProfileId(profileId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ChannelDto getChanelById(String chanelId) {
        ChannelEntity entity = get(chanelId);
        return toDto(entity);
    }

    public ChannelShortInfoDto getChanelByIdShort(String chanelId) {
        ChannelEntity entity = get(chanelId);
        return new ChannelShortInfoDto(entity.getId(), entity.getName(), entity.getPhotoId());
    }

    private ChannelEntity toEntity(ChannelCreateDto dto) {
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setName(dto.getName());
        channelEntity.setDescription(dto.getDescription());
        channelEntity.setStatus(Status.ACTIVE);
        channelEntity.setBannerId(dto.getBannerId());
        channelEntity.setPhotoId(dto.getPhotoId());
        channelEntity.setProfileId(SecurityUtil.getProfileId());
        return channelEntity;
    }

    private ChannelDto toDto(ChannelEntity entity) {
        ChannelDto dto = new ChannelDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setBannerId(dto.getBannerId());
        dto.setPhotoId(dto.getPhotoId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreated(entity.getCreated());
        return dto;
    }

    public ChannelEntity get(String chanelId) {
        return channelRepository.findById(chanelId)
                .orElseThrow(() -> new RuntimeException("Chanel not found"));
    }

    private void isOwner(String chanelId) {
        Long profileId = SecurityUtil.getProfileId();
        ChannelEntity channelEntity = get(chanelId);
        if (!channelEntity.getStatus().equals(Status.ACTIVE)) {
            throw new AppBadException("chanel not active");
        }

        if (!channelEntity.getProfileId().equals(profileId)) {
            throw new AppBadException("You do not have permission to update");
        }
    }

    private void isAdminOrOwner(String chanelId) {
        ProfileEntity profile = SecurityUtil.getProfile();
        ChannelEntity channelEntity = get(chanelId);
        if (!channelEntity.getStatus().equals(Status.ACTIVE)) {
            throw new AppBadException("chanel not active");
        }

        if (!channelEntity.getProfileId().equals(profile.getId())
                || !profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppBadException("You do not have permission to change");
        }
    }
}
