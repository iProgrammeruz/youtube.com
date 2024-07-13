package com.braindevs.dto.comment;

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
public class CommentLikeCreateDto {
    @NotBlank(message = "comment id required")
    private String commentId;
    @NotNull(message = "reaction required")
    private EmotionStatus reaction;
}
