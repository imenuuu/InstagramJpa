package com.example.instagramjpa.dto;

import com.example.instagramjpa.domain.Board;
import com.example.instagramjpa.domain.BoardImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetBoardRes {
    private Long userId;
    private String profileImgUrl;
    private String userLoginId;
    private Long boardId;
    private String description;
    private Boolean likeCheck;
    private int likeCnt;
    private int commentCnt;
    private Timestamp boardTime;
    private List<String> boardImg;

    public static GetBoardRes from(Board board,Boolean b){
        return GetBoardRes.builder().
                userId(board.getUser().getId())
                .profileImgUrl(board.getUser().getProfileImg())
                .userLoginId(board.getUser().getUserId())
                .boardId(board.getId())
                .description(board.getDescription())
                .likeCheck(b)
                .likeCnt(board.getBoardLike().size())
                .commentCnt(board.getComment().size())
                .boardTime(board.getCreatedDate())
                .boardImg(board.getBoardImgUrl().stream().map(BoardImg::getBoardImgurl).collect(Collectors.toList()))
                .build();
    }
}
