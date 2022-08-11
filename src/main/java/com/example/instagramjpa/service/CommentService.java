package com.example.instagramjpa.service;

import com.example.instagramjpa.domain.Board;
import com.example.instagramjpa.domain.Comment;
import com.example.instagramjpa.domain.User;
import com.example.instagramjpa.dto.GetBoardInfoRes;
import com.example.instagramjpa.dto.GetCommentMainRes;
import com.example.instagramjpa.dto.GetCommentRes;
import com.example.instagramjpa.repository.BoardRepository;
import com.example.instagramjpa.repository.CommentLikeRepository;
import com.example.instagramjpa.repository.CommentRepository;
import com.example.instagramjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private  final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;


    public GetCommentMainRes getComments(Long userId,Long boardId, Pageable pageable) {
        System.out.println(boardId);
        Board B=boardRepository.findAllById(boardId) ;
        User u = userRepository.findAllById(B.getUser().getId());

        List<Comment> commentList=commentRepository.findAllByBoardIdAndStatusAndSuspensionStatusOrderByCreatedDateDescWithPagination(boardId,"TRUE","FALSE",pageable);

        GetBoardInfoRes getBoardInfoRes= new GetBoardInfoRes(
                u.getId(),
                u.getProfileImg()
                ,u.getUserId()
                ,B.getId()
                ,B.getDescription()
                ,B.getCreatedDate()
        );

        List<GetCommentRes> getCommentRes=new ArrayList<>();
        for(Comment C : commentList) {
            if(commentLikeRepository.existsByUserIdAndCommentId(userId,C.getBoard().getId()))
            {
                getCommentRes.add(GetCommentRes.toArray(C,true));
            }
            else{
                getCommentRes.add(GetCommentRes.toArray(C,false));
            }
        }
        return new GetCommentMainRes(getBoardInfoRes,getCommentRes);
    }
}
