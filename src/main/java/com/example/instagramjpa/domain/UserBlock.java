package com.example.instagramjpa.domain;

import com.example.instagramjpa.domain.User;
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
public class UserBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blockedUserId", nullable = false)
    private User blockedUser;

    @Column(name = "createdDate", nullable = false,columnDefinition = "timestamp")
    private Timestamp createdDate;
    


}