package com.example.instagramjpa.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "description")
    private String description;

    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updatedDate", nullable = false)
    private Timestamp updatedDate;

    @Column(name = "suspensionStatus", nullable = false, length = 45)
    private String suspensionStatus;

    @Column(name = "status", length = 45)
    private String status;


    public interface BoardId {
        Long getId();
    }

    @OneToMany(mappedBy="board",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BoardImg> boardImgUrl=new HashSet<>();

    @OneToMany(mappedBy="board",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> Comment=new HashSet<>();


    @OneToMany(mappedBy="board",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BoardLike> BoardLike=new HashSet<>();




}