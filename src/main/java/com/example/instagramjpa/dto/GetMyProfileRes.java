package com.example.instagramjpa.dto;

import com.example.instagramjpa.domain.Board;
import com.example.instagramjpa.domain.BoardImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetMyProfileRes {
    private Long userId;
    private String userName;
    private String profileImgUrl;
    private String name;
    private String introduce;
    private String website;
    private Long boardCnt;
    private Long followerCnt;
    private Long followingCnt;
    private List<GetProfileBoardRes> boardList;


    public static GetProfileBoardRes from(Board board) {
        return GetProfileBoardRes.builder()
                .boardId(board.getId())
                .imgUrl(board.getBoardImgUrl().stream().map(
                        BoardImg::getBoardImgurl).collect(Collectors.toList())
                ).build();
    }




}
