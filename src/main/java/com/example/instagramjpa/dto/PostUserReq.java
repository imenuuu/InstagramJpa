package com.example.instagramjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String phoneNumber;
    private String name;
    private String password;
    private LocalDate birth;
    private String userId;
}
