package com.braindevs.dto.video;


import com.braindevs.enums.VideoStatus;
import com.braindevs.enums.VideoType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoCreateDto {

    private String title;
    private String description;
    private VideoStatus status;
    private VideoType type;
    private String previewAttachId;
    private String attachId;
    private Integer categoryId;
    private LocalDateTime createdDate;
    private String channelId;
}
