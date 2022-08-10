package com.example.instagramjpa.controller;

import com.example.instagramjpa.config.BaseResponse;
import com.example.instagramjpa.dto.GetBoardRes;
import com.example.instagramjpa.service.BoardService;
import com.example.instagramjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
