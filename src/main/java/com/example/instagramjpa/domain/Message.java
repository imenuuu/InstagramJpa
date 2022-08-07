package com.example.instagramjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dmRoomId", nullable = false)
    private ChatRoom dmRoom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "dmMessage",columnDefinition = "TEXT")
    private String dmMessage;

    @Column(name = "createdDate", nullable = false,columnDefinition = "timestamp")
    private Timestamp createdDate;

    @Column(name = "status", length = 45)
    private String status;


}