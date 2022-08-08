package com.example.instagramjpa.service;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.domain.User;
import com.example.instagramjpa.domain.UserBlock;
import com.example.instagramjpa.dto.PostBlockReq;
import com.example.instagramjpa.dto.PostLoginReq;
import com.example.instagramjpa.dto.PostUserReq;
import com.example.instagramjpa.dto.PostUserRes;
import com.example.instagramjpa.repository.UserBlockRepository;
import com.example.instagramjpa.repository.UserRepository;
import com.example.instagramjpa.utils.JwtService;
import com.example.instagramjpa.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.instagramjpa.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.instagramjpa.config.BaseResponseStatus.FAILED_TO_LOGIN;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBlockRepository userBlockRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public PostUserRes postUser(PostUserReq postUserReq) throws BaseException {
            User user=User.createUser(postUserReq);
            Long id=userRepository.save(user).getId();

            String jwt= jwtService.createJwt(id);
            return new PostUserRes(id,jwt);
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
            String jwt = jwtService.createJwt(id.getId());
            return new PostUserRes(id.getId(), jwt);
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }




    public void blockUser(PostBlockReq postBlockReq) throws BaseException {
            User createUser = userRepository.getOne(postBlockReq.getUserId());
            User blockedUser = userRepository.getOne(postBlockReq.getBlockedUserId());
            UserBlock userBlock=UserBlock.builder().
                    user(createUser).
                    blockedUser(blockedUser).
                    build();
            userBlockRepository.save(userBlock);
    }
}
