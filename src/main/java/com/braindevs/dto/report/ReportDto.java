package com.braindevs.dto.report;

import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private String id;
    private ProfileDto profile;
    private String content;
    private String entityId;
    private ReportType type;
    private LocalDateTime createdDate;
}
