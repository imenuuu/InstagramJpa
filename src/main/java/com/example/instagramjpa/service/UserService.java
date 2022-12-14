package com.example.instagramjpa.service;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.domain.User;
import com.example.instagramjpa.domain.UserBlock;
import com.example.instagramjpa.dto.*;
import com.example.instagramjpa.repository.*;
import com.example.instagramjpa.utils.JwtService;
import com.example.instagramjpa.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;

import static com.example.instagramjpa.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.instagramjpa.config.BaseResponseStatus.FAILED_TO_LOGIN;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBlockRepository userBlockRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BoardRepository boardRepository;
    private final FollowingRepository followingRepository;
    private final RedisService redisService;

    private String blackListATPrefix;


    public PostUserRes postUser(PostUserReq postUserReq) throws BaseException {
            User user=User.createUser(postUserReq);
            Long id=userRepository.save(user).getId();
            String jwt= jwtService.createJwt(id);
            String refreshToken = jwtService.createRefreshToken(id);
            redisService.saveToken(String.valueOf(id),refreshToken,(1000L*60*60*24*365));
            return new PostUserRes(id,jwt,refreshToken);
    }

    public boolean checkUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public PostUserRes logInUser(PostLoginReq postLoginReq) throws BaseException {
        try {
            User.UserPassword UserPassword = userRepository.findPasswordByUserId(postLoginReq.getUserId());
            String encryptedPassword = SHA256.encrypt(postLoginReq.getPassword());
            if (!encryptedPassword.equals(UserPassword.getPassword())) {
                throw new BaseException(FAILED_TO_LOGIN);
            }

            User.UserId id = userRepository.findIdByUserId(postLoginReq.getUserId());
            Long userId = id.getId();
            String jwt = jwtService.createJwt(userId);
            String refreshToken = jwtService.createRefreshToken(userId);

            redisService.saveToken(String.valueOf(userId),refreshToken,(1000L*60*60*24*365));

            return new PostUserRes(userId, jwt,refreshToken);
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }




    public void blockUser(PostBlockReq postBlockReq) throws BaseException {
        try {
            User createUser = userRepository.getOne(postBlockReq.getUserId());
            User blockedUser = userRepository.getOne(postBlockReq.getBlockedUserId());
            UserBlock userBlock = UserBlock.builder().
                    user(createUser).
                    blockedUser(blockedUser).
                    build();
            userBlockRepository.save(userBlock);
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkUserBlock(Long userId, Long targetId) {
        return userBlockRepository.existsByUserIdAndBlockedUserId(userId,targetId);
    }

    public void modifyProfileImage(PatchProfileImgReq patchProfileImgReq) {
        User user=userRepository.getOne(patchProfileImgReq.getUserId());
        user.updateImg(patchProfileImgReq.getProfileImgUrl());
        userRepository.save(user);
    }

    public void updateLogInDate(Long userId) {
        User user=userRepository.getOne(userId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.updateLoginDate(now);
        userRepository.save(user);
    }

    public void modifyProfile(PatchProfileReq patchProfileReq) {
        User user=userRepository.getOne(patchProfileReq.getUserId());
        user.updateProfile(patchProfileReq.getName(),patchProfileReq.getUserLogInId(),
                patchProfileReq.getWebsite(),patchProfileReq.getIntroduce());
        userRepository.save(user);
    }

    public void modifyPassword(PatchPasswordReq patchPasswordReq) {
        User user=userRepository.getOne(patchPasswordReq.getUserId());
        user.updatePassword(SHA256.encrypt(patchPasswordReq.getPassword()));
        userRepository.save(user);
    }

    public boolean checkUserInvalid(PatchPasswordReq patchPasswordReq) {
        return userRepository.existsByIdAndPhoneNumber(patchPasswordReq.getUserId(),  patchPasswordReq.getPhoneNumber());
    }

    public GetMyProfileRes getMyProfile(Long userId) {
        User user =userRepository.getOne(userId);
        return GetMyProfileRes.builder()
                .userId(user.getId())
                .userLogInId(user.getUserId())
                .profileImgUrl(user.getProfileImg())
                .introduce(user.getIntroduce())
                .name(user.getName())
                .website(user.getWebSite())
                .boardCnt(boardRepository.countByUserId(userId))
                .followerCnt(followingRepository.countByUserId(userId))
                .followingCnt(followingRepository.countByFollowUserId(userId))
                .boardList(boardRepository.findIdByUserId(user.getId()).stream().map(
                        GetMyProfileRes::from).collect(Collectors.toList()))
                .build();

    }

    public boolean checkUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    public PostUserRes reIssueToken(Long userId) {

        String accessToken= jwtService.createJwt(userId);
        String refreshToken = jwtService.createRefreshToken(userId);

        redisService.saveToken(String.valueOf(userId),refreshToken,(1000L*60*60*24*365));
        return new PostUserRes(userId,accessToken,refreshToken);
    }

    public void logOut(Long userId, String accessToken) {



        redisService.deleteValues(String.valueOf(userId));
    }
}
