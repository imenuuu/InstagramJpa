package com.example.instagramjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchProfileReq {
    private Long userId;
    private String name;
    private String userLogInId;
    private String website;
    private String introduce;
}
