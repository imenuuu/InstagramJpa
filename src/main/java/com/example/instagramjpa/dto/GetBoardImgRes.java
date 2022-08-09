package com.example.instagramjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBoardImgRes {
    private Long imgId;
    private String imgUrl;
}
