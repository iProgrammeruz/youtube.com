package com.braindevs.dto.playList;

import com.braindevs.dto.channel.ChannelDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.enums.PlayListStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListDto {
    private Long id;
    private String name;
    private String description;
    private PlayListStatus status;
    private String chanelId;
    private ChannelDto chanel;
    private Long profileId;
    private ProfileDto profile;
    private int orderNumber;
    private LocalDateTime createdDate;
    private Long videoCount;
    private Integer totalViewCount;
    private List<VideoDto> videoList;
}
