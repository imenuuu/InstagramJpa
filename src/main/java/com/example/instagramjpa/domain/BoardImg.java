package com.example.instagramjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class BoardImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @Column(name = "boardImgurl", nullable = false,columnDefinition = "TEXT")
    private String boardImgurl;

    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updatedDate", nullable = false)
    private Timestamp updatedDate;


    public interface ImgUrl{
        String getBoardImgUrl();
    }


}