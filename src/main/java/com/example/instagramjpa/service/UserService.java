package com.example.instagramjpa.service;

import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.domain.User;
import com.example.instagramjpa.dto.PostUserReq;
import com.example.instagramjpa.dto.PostUserRes;
import com.example.instagramjpa.repository.UserRepository;
import com.example.instagramjpa.utils.JwtService;
import com.example.instagramjpa.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    public PostUserRes postUser(PostUserReq postUserReq) throws BaseException {
            User user=User.createUser(postUserReq);

            Long id=userRepository.save(user).getId();
            System.out.println(user.getUserId());
            String jwt= jwtService.createJwt(id);
            return new PostUserRes(id,jwt);
    }
}
