package com.braindevs.dto.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDto {
    private Long id;
    private String message;
    private String email;
    private LocalDateTime createdDate;
}
