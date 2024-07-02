package com.braindevs.dto.channnel;

import com.braindevs.dto.AttachDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDto {

    private String id;
    private String name;
    private String photoId;
    private AttachDto photo;
    private String description;
    private Status status;
    private String bannerId;
    private AttachDto banner;
    private Long ownerId;
    private ProfileDto owner;
    private LocalDateTime createdDate;


}
