package com.example.instagramjpa.dto;

import com.example.instagramjpa.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetCommentRes {
    private Long userId;
    private String profileImgUrl;
    private String userLogInId;
    private Long commentId;
    private String comment;
    private int likeCnt;
    private int reCommentCnt;
    private boolean likeCheck;
    private Timestamp commentTime;

    public static GetCommentRes toArray(Comment C, Boolean b){
        return GetCommentRes.builder()
                .userId(C.getUser().getId())
                .profileImgUrl(C.getUser().getProfileImg())
                .userLogInId(C.getUser().getUserId())
                .commentId(C.getId())
                .comment(C.getComment())
                .likeCnt(C.getCommentLike().size())
                .reCommentCnt(C.getReComment().size())
                .likeCheck(b)
                .commentTime(C.getCreatedDate())
                .build();


    }
}
