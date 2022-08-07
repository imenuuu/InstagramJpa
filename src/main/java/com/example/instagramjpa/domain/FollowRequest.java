package com.example.instagramjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Timestamp;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class FollowRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requestUserId", nullable = false)
    private User requestUser;

    @Column(name = "createdDate",columnDefinition = "timestamp")
    private Timestamp createdDate;

    @Column(name = "status", length = 45)
    private String status;



}