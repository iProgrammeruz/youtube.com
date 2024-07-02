package com.braindevs.service;

import com.braindevs.dto.channnel.ChannelCreateDto;
import com.braindevs.dto.channnel.ChannelDto;
import com.braindevs.dto.channnel.ChannelUpdateDto;
import com.braindevs.entity.ChannelEntity;
import com.braindevs.enums.ProfileRole;
import com.braindevs.enums.Status;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.ChannelRepository;
import com.braindevs.repository.ProfileRepository;
import com.braindevs.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ChannelService {
    private final ChannelRepository channelRepository;
    private final AttachService attachService;


    public ChannelDto create(ChannelCreateDto dto) {
        findByName(dto.getName());
        ChannelEntity save = channelRepository.save(toEntity(dto));
        return toDto(save);

    }


    public ChannelDto toDto(ChannelEntity entity) {
        ChannelDto dto = new ChannelDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setBannerId(entity.getBannerId());
        dto.setStatus(entity.getStatus());
        dto.setOwnerId(entity.getOwnerId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhotoId(entity.getAttachId());
        return dto;
    }
    public ChannelEntity toEntity(ChannelCreateDto dto) {
        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setBannerId(dto.getBannerId());
        entity.setStatus(Status.ACTIVE);
        entity.setOwnerId(SecurityUtil.getProfileId());
        entity.setAttachId(dto.getAttachId());
        return entity;
    }
    public void findByName(String name) {
        channelRepository.findByName(name)
                .ifPresent(channel -> {
                    throw new AppBadException("channel already exist");
                });
    }

    public ChannelEntity findById(String id){
        return channelRepository.findById(id)
                .orElseThrow(() -> new AppBadException("channel not found"));

    }

    public void isOwner(String channelId){
        channelRepository.findByOwnerIdAndId(SecurityUtil.getProfileId(), channelId)
                .orElseThrow(() -> new AppBadException("You are not owner of this channel"));

    }

    public ChannelDto update(ChannelUpdateDto dto, String channelId) {
        ChannelEntity channelEntity = findById(channelId);
        if (dto.getName() != null) {
            channelEntity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            channelEntity.setDescription(dto.getDescription());
        }
        ChannelEntity save = channelRepository.save(channelEntity);

        return toDto(save);
    }

    public ChannelDto updatePhoto(String channelId, String photoId) {
        ChannelEntity channelEntity = findById(channelId);
        isOwner(channelId);
        String old = channelEntity.getAttachId();
        channelEntity.setAttachId(photoId);
        ChannelEntity save = channelRepository.save(channelEntity);
        attachService.delete(old);
        return toDto(save);
    }

    public ChannelDto updateBanner(String channelId, String bannerId) {
        ChannelEntity channelEntity = findById(channelId);
        isOwner(channelId);
        String old = channelEntity.getBannerId();
        channelEntity.setBannerId(bannerId);
        ChannelEntity save = channelRepository.save(channelEntity);
        attachService.delete(old);
        return toDto(save);
    }

    public Page<ChannelDto> getChannels(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ChannelEntity> channelEntities =channelRepository.findAll(pageable);
        List<ChannelDto> list = new LinkedList<>();
        for (ChannelEntity channelEntity : channelEntities) {
            list.add(toDto(channelEntity));
        }
        Long totalElement = channelEntities.getTotalElements();
        return new PageImpl<>(list, pageable, totalElement);
    }


    public ChannelDto getById(String channelId) {
        return toDto(findById(channelId));
    }




        public void changeStatus(String channelId,Status status) {
        isAdminOrOwner(channelId);
        channelRepository.updateStatus(channelId,status);
    }

    public void isAdminOrOwner(String channelId) {
       if(findById(channelId).getOwnerId()!=SecurityUtil.getProfileId()
               || SecurityUtil.getProfile().getRole().equals(ProfileRole.ROLE_ADMIN)){
           throw new AppBadException("You are not owner of this channel");
       }
    }

    public Page<ChannelDto> getByUserChannelList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ChannelEntity> channelEntities =channelRepository.findAllByOwnerId(SecurityUtil.getProfileId(),pageable);
        List<ChannelDto> list = new LinkedList<>();
        for (ChannelEntity channelEntity : channelEntities) {
            list.add(toDto(channelEntity));
        }
        Long totalElement = channelEntities.getTotalElements();
        return new PageImpl<>(list, pageable, totalElement);
    }
}
