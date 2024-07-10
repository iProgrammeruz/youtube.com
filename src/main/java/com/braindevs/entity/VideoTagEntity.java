package com.braindevs.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
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
    private Integer tagId;
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TagEntity tag;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
