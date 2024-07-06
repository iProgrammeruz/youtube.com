package com.braindevs.dto.chanel;

import com.braindevs.dto.AttachDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChanelDto {
    private String id;
    private String name;
    private String description;
    private Status status;
    private String photoId;
    private AttachDto photo;
    private String bannerId;
    private AttachDto banner;
    private Long profileId;
    private ProfileDto profile;
    private LocalDateTime created;
}
