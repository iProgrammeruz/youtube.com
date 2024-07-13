package com.braindevs.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TagDto {
    private String id ;

    @NotBlank(message = "Tag name is required")
    private String name ;

    private LocalDateTime createdDate;
}
