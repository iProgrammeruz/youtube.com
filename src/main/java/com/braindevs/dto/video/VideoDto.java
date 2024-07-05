package com.braindevs.dto.video;
import com.braindevs.enums.VideoStatus;
import com.braindevs.enums.VideoType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoDto {

    private String previewAttachId;
    private String title;
    private Integer categoryId;
    private String attachId;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private VideoStatus status;
    private VideoType type;
    private String description;
    private String channelId;


}
