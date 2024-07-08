package com.braindevs.dto.video;

import com.braindevs.dto.attach.AttachShortInfoDto;
import com.braindevs.dto.channel.ChannelDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoShortInfoDto {

    private String id;
    private String title;
    private AttachShortInfoDto previewAttach;
    private LocalDateTime publishedDate;
    private ChannelDto channel;
    private Integer viewCount;

}
