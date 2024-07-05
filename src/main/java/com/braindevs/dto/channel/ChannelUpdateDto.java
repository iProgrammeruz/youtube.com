package com.braindevs.dto.channel;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelUpdateDto {
    @Size(min = 3, message = "name must be at least 3 characters")
    private String name;
    @Size(min = 10, message = "description must be at least 10 characters")
    private String description;
}
