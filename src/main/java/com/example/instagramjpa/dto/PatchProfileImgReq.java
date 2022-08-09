package com.example.instagramjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchProfileImgReq {
    private Long userId;
    private String profileImgUrl;
}
