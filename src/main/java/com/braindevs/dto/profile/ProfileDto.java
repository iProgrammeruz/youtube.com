package com.braindevs.dto.profile;

import com.braindevs.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String mainPhotoUrl;
    private LocalDateTime createdDate;
    private ProfileRole role;
    private String jwt;
}
