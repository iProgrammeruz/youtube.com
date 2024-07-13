package com.braindevs.dto.comment;

import com.braindevs.enums.EmotionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeDto {
    private String id;
    private Long userId;
    private String commentId;
    private EmotionStatus reaction;
    private LocalDateTime createdDate;
}
