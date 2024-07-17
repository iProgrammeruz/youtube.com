package com.braindevs.dto.video;

import com.braindevs.enums.EmotionStatus;
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
public class VideoLikeCreateDto {
    @NotBlank(message = "video id required")
    private String videoId;
    @NotNull(message = "reaction required")
    private EmotionStatus reaction;
}
