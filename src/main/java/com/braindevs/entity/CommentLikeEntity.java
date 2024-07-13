package com.braindevs.entity;

import com.braindevs.enums.EmotionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "profile_id")
    private Long profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private String commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Column(name = "reaction")
    @Enumerated(EnumType.STRING)
    private EmotionStatus reaction;

    @Builder.Default
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
