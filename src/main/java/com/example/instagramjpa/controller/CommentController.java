package com.example.instagramjpa.controller;

import com.example.instagramjpa.config.BaseResponse;
import com.example.instagramjpa.dto.GetCommentMainRes;
import com.example.instagramjpa.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseBody
    @GetMapping("/{userId}/{boardId}")
    public BaseResponse<GetCommentMainRes> getComments(@PathVariable("userId") Long userId,@PathVariable("boardId") Long boardId,Pageable pageable){
        System.out.println(boardId);
        GetCommentMainRes getCommentMainRes=commentService.getComments(userId,boardId, pageable);
        return new BaseResponse<>(getCommentMainRes);
    }
}
