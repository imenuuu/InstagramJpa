package com.example.instagramjpa.domain;

import com.example.instagramjpa.dto.PostUserReq;
import com.example.instagramjpa.utils.SHA256;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import java.security.Timestamp;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false, length = 45)
    private String userId;

    @Lob
    @Column(name = "profileImg",columnDefinition = "TEXT")
    private String profileImg;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "password",columnDefinition = "TEXT")
    private String password;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "webSite", length = 45)
    private String webSite;

    @Column(name = "introduce", length = 45)
    private String introduce;

    @Column(name = "phoneNumber", nullable = false, length = 45)
    private String phoneNumber;

    @Column(name = "userReact", length = 45)
    private String userReact;

    @Column(name = "userStatus", length = 45)
    private String userStatus;

    @Column(name = "createdDate",columnDefinition = "timestamp")
    private Timestamp createdDate;

    @Column(name = "updatedDate",columnDefinition = "timestamp")
    private Timestamp updatedDate;

    @Column(name = "logInDate",columnDefinition = "timestamp")
    private Timestamp logInDate;

    @Column(name = "userPublic", length = 45)
    private String userPublic;

    @Column(name = "agreeInfo", length = 45)
    private String agreeInfo;

    @Column(name = "suspensionStatus", length = 45)
    private String suspensionStatus;

    @Builder
    public User(String phoneNumber,String name,String password,LocalDate birth,String userId){
        this.phoneNumber=phoneNumber;
        this.name=name;
        this.password=password;
        this.birth=birth;
        this.userId=userId;
    }

    public static User createUser(PostUserReq postUserReq){
        return User.builder().
                phoneNumber(postUserReq.getPhoneNumber()).
                name(postUserReq.getName()).
                password(SHA256.encrypt(postUserReq.getPassword())).
                birth(postUserReq.getBirth()).
                userId(postUserReq.getUserId()).
                build();
    }

}