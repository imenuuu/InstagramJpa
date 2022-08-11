package com.example.instagramjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetBoardInfoRes {
    private Long userId;
    private String profileImgUrl;
    private String userLogInId;
    private Long boardId;
    private String description;
    private Timestamp boardTime;
}
