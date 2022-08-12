package com.example.instagramjpa.domain;

import com.example.instagramjpa.dto.PostBoardReq;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DynamicInsert
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

    @Column(name = "createdDate")
    private Timestamp createdDate;

    @Column(name = "updatedDate")
    private Timestamp updatedDate;

    @Column(name = "suspensionStatus", length = 45)
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