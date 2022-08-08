package com.example.instagramjpa.controller;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.config.BaseResponse;
import com.example.instagramjpa.config.BaseResponseStatus;
import com.example.instagramjpa.domain.User;
import com.example.instagramjpa.dto.PostBlockReq;
import com.example.instagramjpa.dto.PostLoginReq;
import com.example.instagramjpa.dto.PostUserReq;
import com.example.instagramjpa.dto.PostUserRes;
import com.example.instagramjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.instagramjpa.config.BaseResponseStatus.*;
import static com.example.instagramjpa.utils.ValidationRegex.isRegexId;
import static com.example.instagramjpa.utils.ValidationRegex.isRegexPhoneNumber;

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
            if(userService.checkUserId(postUserReq.getUserId())){
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_EXISTS_ID);
            }
            if(postUserReq.getUserId().length() <1){
                return new BaseResponse<>(POST_USERS_EMPTY_ID);
            }
            if(postUserReq.getUserId().length()>=20){
                return new BaseResponse<>(LONG_USER_ID_CHARACTERS);
            }
            //아이디 정규표현
            if (!isRegexId(postUserReq.getUserId())) {
                return new BaseResponse<>(POST_USERS_INVALID_ID);
            }
            if(!isRegexPhoneNumber(postUserReq.getPhoneNumber())){
                return new BaseResponse<>(POST_USERS_INVALID_PHONE);
            }
            if(userService.checkPhoneNumber(postUserReq.getPhoneNumber())){
                return new BaseResponse<>(POST_USERS_PHONE_NUMBER);
            }
            PostUserRes postUserRes = userService.postUser(postUserReq);
            System.out.println(postUserRes.getUserId());
            return new BaseResponse<>(postUserRes);
        }catch(BaseException e){
            return new BaseResponse<>((e.getStatus()));

        }
    }

    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostUserRes> logInUser(@RequestBody PostLoginReq postLoginReq){
        try {
            if(!userService.checkUserId(postLoginReq.getUserId())){
                return new BaseResponse<>(FAILED_TO_LOGIN);
            }
            PostUserRes postUserRes= userService.logInUser(postLoginReq);
            return new BaseResponse<>(postUserRes);

        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping(("/block/{userId}/{targetId}"))
    public BaseResponse<String> blockUser(@PathVariable("userId") Long userId, @PathVariable("targetId") Long targetId){
        try{
            PostBlockReq postBlockReq = new PostBlockReq(userId,targetId);
            userService.blockUser(postBlockReq);
            String result="유저 차단 성공";
            return new BaseResponse<>(result);
        }catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }
}
