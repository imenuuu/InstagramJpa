package com.example.instagramjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "kakaoEmail", nullable = false, length = 45)
    private String kakaoEmail;

    @Column(name = "kakaoName", nullable = false, length = 45)
    private String kakaoName;

    @Column(name = "kakaoId", nullable = false)
    private Long kakaoId;

    @Column(name = "status", length = 45)
    private String status;



}