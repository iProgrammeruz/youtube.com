package com.braindevs.mapper;

import java.time.LocalDateTime;

public interface SubscriptionMapper {
    String getId();
    String getChanelId();
    String getChannelName();
    String getChanelPhotoId();
    LocalDateTime getChanelCreatedDate();
}
