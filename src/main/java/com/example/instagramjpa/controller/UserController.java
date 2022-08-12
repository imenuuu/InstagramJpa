package com.example.instagramjpa.controller;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.config.BaseResponse;
import com.example.instagramjpa.config.BaseResponseStatus;
import com.example.instagramjpa.dto.*;
import com.example.instagramjpa.service.RedisService;
import com.example.instagramjpa.service.UserService;
import com.example.instagramjpa.utils.JwtService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.instagramjpa.config.BaseResponseStatus.*;
import static com.example.instagramjpa.utils.ValidationRegex.isRegexId;
import static com.example.instagramjpa.utils.ValidationRegex.isRegexPhoneNumber;

@RestController
@RequestMapping("/users")
@Api(tags={"User API"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final RedisService redisService;

    public UserController(JwtService jwtService, RedisService redisService) {
        this.jwtService = jwtService;
        this.redisService = redisService;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String getHello(){
        System.out.println("hello");
        return "hello";
    }

    //회원가입
    @ResponseBody
    @PostMapping("")
    @ApiOperation(value="회원가입",notes="회원가입 API")

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
    @ApiOperation(value="로그인",notes="로그인 API")
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
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공.")
            , @ApiResponse(code = 2001, message = "JWT를 입력해주세요")
            , @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다")
            , @ApiResponse(code = 2003, message = "권한이 없는 유저 접근입니다.")
            , @ApiResponse(code = 2011, message = "존재하지 않는 유저입니다.")
            , @ApiResponse(code = 2094, message = "이미 해당 유저를 차단 하고 있습니다.")
            , @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패 하였습니다.")
    })
    @ApiOperation(value="차단",notes="차단 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "차단하고자 하는 유저 ID값",dataType = "Long",paramType = "path",defaultValue = "None")
            ,
            @ApiImplicitParam(name = "userId", value = "차단하고자 하는 유저 ID값",dataType = "Long",paramType = "path",defaultValue = "None")
            ,@ApiImplicitParam(name="X-ACCESS-TOKEN",value = "JWT 토큰값",dataType = "String",paramType = "header")

    }
    )
    public BaseResponse<String> blockUser(@PathVariable("userId") Long userId, @PathVariable("targetId") Long targetId){
        try{
            Long userIdByJwt = jwtService.getUserIdx();
            System.out.println(userIdByJwt);
            //userId와 접근한 유저가 같은지 확인
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }if(!userService.checkUserById(userId)){
                return new BaseResponse<>(NOT_EXIST_USER);
            }if(!userService.checkUserById(targetId)){
                return new BaseResponse<>(NOT_EXIST_USER);
            }
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

    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공.")
            , @ApiResponse(code = 2001, message = "JWT를 입력해주세요")
            , @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다")
            , @ApiResponse(code = 2003, message = "권한이 없는 유저 접근입니다.")
            , @ApiResponse(code = 2011, message = "존재하지 않는 유저입니다.")
            , @ApiResponse(code = 2094, message = "이미 해당 유저를 차단 하고 있습니다.")
            , @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패 하였습니다.")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name="X-ACCESS-TOKEN",value = "JWT 토큰값",dataType = "String",paramType = "header")

    })
    @ResponseBody
    @PatchMapping("/profile/img")
    @ApiOperation(value="프로필 이미지 변경",notes="프로필 이미지 API")
    public BaseResponse<String> modifyProfileImage(@RequestBody PatchProfileImgReq patchProfileImgReq){
        String result="이미지 변경 성공";
        userService.modifyProfileImage(patchProfileImgReq);
        return new BaseResponse<>(result);
    }

    @ResponseBody
    @PatchMapping("/profile")
    @ApiOperation(value="프로필 편집",notes="프로필 편집 API")
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
    @ApiOperation(value="비밀번호 변경",notes="비밀번호 변경 API")

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

    @ResponseBody
    @PostMapping("/re_token")
    public BaseResponse<PostUserRes> reIssueToken(@RequestBody PostReIssueReq postReIssueReq){
        if(!userService.checkUserById(postReIssueReq.getUserId())){
            return new BaseResponse<>(NOT_EXIST_USER);
        }

        String redisRT= redisService.getValues(String.valueOf(postReIssueReq.getUserId()));
        if(redisRT==null){
            return new BaseResponse<>(INVALID_REFRESH_TOKEN);

        }
        if(!redisRT.equals(postReIssueReq.getRefreshToken())){
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        PostUserRes postUserRes=userService.reIssueToken(postReIssueReq.getUserId());

        return new BaseResponse<>(postUserRes);

    }
}
