package com.braindevs.dto.playlistVideo;

import com.braindevs.dto.channel.ChannelDto;
import com.braindevs.dto.playList.PlayListDto;
import com.braindevs.dto.video.VideoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistVideoDto {
    private String id;
    private Long playlistId;
    private PlayListDto playList;
    private String videoId;
    private VideoDto video;
    private Integer orderNumber;
    private LocalDateTime createdDate;
    private ChannelDto chanel;
}
