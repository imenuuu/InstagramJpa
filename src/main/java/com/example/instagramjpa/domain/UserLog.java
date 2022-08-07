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
public class UserLog {
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