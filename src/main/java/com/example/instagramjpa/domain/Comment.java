package com.example.instagramjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "comment", nullable = false, length = 45)
    private String comment;

    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;

    @Column(name = "status", length = 45)
    private String status;

    @Column(name = "suspensionStatus", length = 45)
    private String suspensionStatus;

    @OneToMany(mappedBy="comment",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<ReComment> ReComment=new HashSet<>();



}