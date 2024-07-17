package com.braindevs.dto.subscription;

import com.braindevs.dto.channel.ChannelDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private String id;
    private Long profileId;
    private ProfileDto profile;
    private String channelId;
    private ChannelDto channel;
    private LocalDateTime createdDate;
    private LocalDateTime unsubscribeDate;
    private Status subscriptionStatus;
//    private NotificationType notificationType;
}
