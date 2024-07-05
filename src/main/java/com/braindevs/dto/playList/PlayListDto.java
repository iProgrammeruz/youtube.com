package com.braindevs.dto.playList;

import com.braindevs.dto.chanel.ChanelDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.enums.PlayListStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListDto {
    private Long id;
    private String name;
    private String description;
    private PlayListStatus status;
    private String chanelId;
    private ChanelDto chanel;
    private Long profileId;
    private ProfileDto profile;
    private int orderNumber;
    private LocalDateTime createdDate;
    private Integer videoCount;
}
