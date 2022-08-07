package com.example.instagramjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatRoomJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dmRoomId", nullable = false)
    private ChatRoom dmRoom;

    @Column(name = "status", nullable = false, length = 45)
    private String status;



}