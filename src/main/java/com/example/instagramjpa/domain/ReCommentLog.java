package com.example.instagramjpa.domain;

import javax.persistence.*;
import java.security.Timestamp;


@Entity
public class ReCommentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "createdDate", nullable = false,columnDefinition = "timestamp")
    private Timestamp createdDate;


}