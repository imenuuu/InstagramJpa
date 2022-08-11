package com.example.instagramjpa.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentMainRes {
    private GetBoardInfoRes boardInfo;
    private List<GetCommentRes> commentList;



}
