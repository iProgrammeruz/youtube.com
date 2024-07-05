package com.braindevs.entity;

import com.braindevs.enums.VideoStatus;
import com.braindevs.enums.VideoType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoEntity {
    /*id(uuid), preview_attach_id,title,category_id,attach_id,created_date,published_date,status(private,public),
    type(video,short),view_count,shared_count,description,channel_id,(like_count,dislike_count),

    view_count -> Okala view_count buyerda ham bo'lsin. Alohida Table ham bo'lsin.
    category_id -> bitta video bitta category bo'lsin.*/

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "preview_attach_id")
    private String previewAttachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity previewAttach;

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "title")
    private String title;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VideoStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private VideoType type;

    @Column(name = "view_count")
    private Integer viewCount; //TODO trigger at view_count entity

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "like_count")
    private Integer likeCount = 0; //TODO trigger at video_like entity

    @Column(name = "dislike_count")
    private Integer dislikeCount = 0;





}
