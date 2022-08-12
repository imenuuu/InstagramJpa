package com.example.instagramjpa.controller;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.config.BaseResponse;
import com.example.instagramjpa.dto.BoardImg;
import com.example.instagramjpa.dto.GetBoardRes;
import com.example.instagramjpa.dto.PostBoardReq;
import com.example.instagramjpa.service.BoardService;
import com.example.instagramjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.instagramjpa.config.BaseResponseStatus.LONG_NUMBER_CHARACTERS;
import static com.example.instagramjpa.config.BaseResponseStatus.MANY_PHOTO_BOARD;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private  BoardService boardService;

    @ResponseBody
    @GetMapping("/{userId}")
    private BaseResponse<List<GetBoardRes>> getMainBoard(@PathVariable("userId") Long userId, Pageable pageable){
        List<GetBoardRes> getBoardRes=boardService.getMainBoard(userId,pageable);
        return new BaseResponse<>(getBoardRes);
    }


    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> createBoard(@RequestBody PostBoardReq postBoardReq){


        if(postBoardReq.getBoardImg().size()>10){
            return new BaseResponse<>(MANY_PHOTO_BOARD);
        }
        if(postBoardReq.getDescription().length()>=1000){
            return new BaseResponse<>(LONG_NUMBER_CHARACTERS);
        }
        Long lastInsertId = boardService.createBoard(postBoardReq);
        for(BoardImg boardImg: postBoardReq.getBoardImg()){
            boardService.createBoardImg(postBoardReq.getUserId(),lastInsertId,boardImg.getBoardImgUrl());
        }
        String result="게시글 등록 성공";
        return new BaseResponse<>(result);
    }

}
