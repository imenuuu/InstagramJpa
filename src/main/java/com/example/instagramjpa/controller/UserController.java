package com.example.instagramjpa.controller;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.config.BaseResponse;
import com.example.instagramjpa.config.BaseResponseStatus;
import com.example.instagramjpa.domain.User;
import com.example.instagramjpa.dto.*;
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

    //회원가입
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
            userService.updateLogInDate(postUserRes.getUserId());
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
            userService.updateLogInDate(postUserRes.getUserId());
            return new BaseResponse<>(postUserRes);

        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping(("/block/{userId}/{targetId}"))
    public BaseResponse<String> blockUser(@PathVariable("userId") Long userId, @PathVariable("targetId") Long targetId){
        try{
            if(userService.checkUserBlock(userId, targetId)){
                return new BaseResponse<>(EXIST_BLOCK_USER);
            }
            PostBlockReq postBlockReq = new PostBlockReq(userId,targetId);
            userService.blockUser(postBlockReq);
            String result="유저 차단 성공";
            return new BaseResponse<>(result);
        }catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/profile/img")
    public BaseResponse<String> modifyProfileImage(@RequestBody PatchProfileImgReq patchProfileImgReq){
        String result="이미지 변경 성공";
        userService.modifyProfileImage(patchProfileImgReq);
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @PatchMapping("/profile")
    public BaseResponse<String> modifyProfile(@RequestBody PatchProfileReq patchProfileReq){
        String result="프로필 편집 성공";
        if(userService.checkUserId(patchProfileReq.getUserLogInId())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EXISTS_ID);
        }
        if(patchProfileReq.getUserLogInId().length() <1){
            return new BaseResponse<>(POST_USERS_EMPTY_ID);
        }
        if(patchProfileReq.getUserLogInId().length()>=20){
            return new BaseResponse<>(LONG_USER_ID_CHARACTERS);
        }
        //아이디 정규표현
        if (!isRegexId(patchProfileReq.getUserLogInId())) {
            return new BaseResponse<>(POST_USERS_INVALID_ID);
        }
        userService.modifyProfile(patchProfileReq);
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @PatchMapping("/password")
    public BaseResponse<String> modifyPassword(@RequestBody PatchPasswordReq patchPasswordReq){
        String result="비밀번호 변경 성공";
        if(!userService.checkUserInvalid(patchPasswordReq)){
            return new BaseResponse<>(NOT_SUCCESS_USER_INFO);
        }
        userService.modifyPassword(patchPasswordReq);
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @GetMapping("/my_profile/{userId}")
    public BaseResponse<GetMyProfileRes> getMyProfile(@PathVariable("userId") Long userId){
        return new BaseResponse<>(userService.getMyProfile(userId));
    }
}
