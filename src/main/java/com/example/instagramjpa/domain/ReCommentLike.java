package com.example.instagramjpa.domain;

import javax.persistence.*;
import java.security.Timestamp;


@Entity
public class ReCommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reCommentId", nullable = false)
    private ReComment reComment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "createdDate", nullable = false,columnDefinition = "timestamp")
    private Timestamp createdDate;

    @Column(name = "status", length = 45)
    private String status;



}