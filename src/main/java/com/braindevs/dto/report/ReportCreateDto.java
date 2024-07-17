package com.braindevs.dto.report;

import com.braindevs.enums.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateDto {
    @NotBlank(message = "content cannot be null")
    private String content;

    @NotBlank
    private String entityId;

    @NotNull
    private ReportType type;
}
