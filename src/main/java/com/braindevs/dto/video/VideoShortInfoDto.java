package com.braindevs.dto.video;

import com.braindevs.dto.attach.AttachShortInfoDto;
import com.braindevs.dto.channel.ChannelShortInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoShortInfoDto {

    private String id;
    private String title;
    private Integer viewCount;
    private LocalDateTime publishedDate;
    private AttachShortInfoDto previewAttach;
    private ChannelShortInfoDto channel;

}
