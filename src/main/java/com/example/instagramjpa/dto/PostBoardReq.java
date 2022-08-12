package com.example.instagramjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostBoardReq {
    private List<BoardImg> boardImg;
    private Long userId;
    private String description;
}
