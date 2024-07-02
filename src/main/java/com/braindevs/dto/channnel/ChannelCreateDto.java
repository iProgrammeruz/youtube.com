package com.braindevs.dto.channnel;


import com.braindevs.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChannelCreateDto {
    @NotBlank(message = "name is required")
    private String name;
    private String attachId;
    @NotBlank(message = "some desc is required")
    private String description;
    private String bannerId;

}
