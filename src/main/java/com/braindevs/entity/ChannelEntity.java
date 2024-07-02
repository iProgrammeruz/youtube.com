package com.braindevs.entity;


import com.braindevs.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name")
    private String name ;

    @Column(name = "attach_id")
    private String attachId;
    @OneToOne()
    @JoinColumn(name = "attach_id" ,insertable = false,updatable = false)
    private AttachEntity attach;

    @Column(name = "description" , columnDefinition = "text")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "banner_id")
    private String bannerId;
    @OneToOne()
    @JoinColumn(name = "banner_id" ,insertable = false,updatable = false)
    private AttachEntity banner;

    @Column(name = "owner_id")
    private Long ownerId ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",insertable = false, updatable = false)
    private ProfileEntity owner;


    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

}
