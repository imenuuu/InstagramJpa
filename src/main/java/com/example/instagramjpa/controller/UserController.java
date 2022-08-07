package com.example.instagramjpa.controller;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.config.BaseResponse;
import com.example.instagramjpa.dto.PostUserReq;
import com.example.instagramjpa.dto.PostUserRes;
import com.example.instagramjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/hello")
    public String getHello(){
        System.out.println("hello");
        return "hello";
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> postUser(@RequestBody PostUserReq postUserReq){
        try {
            PostUserRes postUserRes = userService.postUser(postUserReq);
            System.out.println(postUserRes.getUserId());
            return new BaseResponse<>(postUserRes);
        }catch(BaseException e){
            return new BaseResponse<>((e.getStatus()));

        }
    }
}
