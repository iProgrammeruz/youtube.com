package com.braindevs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "video_tag")
public class VideoTagEntity {


    @Id
    @UuidGenerator
    private String id;

    @Column(name = "video_id")
    private String videoId;
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VideoEntity video;

    @Column(name = "tag_id")
    private String tagId;
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TagEntity tag;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
