package com.braindevs.dto.video;
import com.braindevs.enums.VideoStatus;
import com.braindevs.enums.VideoType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoDto {

    /*id(uuid), preview_attach_id,title,category_id,attach_id,created_date,published_date,
    status(private,public), type(video,short),view_count,shared_count,description,channel_id,(like_count,dislike_count),

    view_count -> Okala view_count buyerda ham bo'lsin. Alohida Table ham bo'lsin.
            category_id -> bitta video bitta category bo'lsin.*/

    private String previewAttachId;
    private String title;
    private String categoryId;
    private String attachId;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private VideoStatus status;
    private VideoType type;
    private String description;
    private String channelId;


}
