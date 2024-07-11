package com.braindevs.mapper;

import com.braindevs.enums.PlayListStatus;

import java.time.LocalDateTime;

public interface PlaylistFullInfoMapper {
    Long getId();
    String getName();
    String getDescription();
    PlayListStatus getStatus();
    Integer getOrderNumber();
    String getChanelId();
    String getChanelName();
    String getChanelPhotoId();
    Long getProfileId();
    String getProfileName();
    String getProfileSurname();
    String getProfilePhotoId();
    LocalDateTime getCreatedDate();
}
