package com.example.instagramjpa.utils;


import com.example.instagramjpa.config.BaseException;
import com.example.instagramjpa.config.secret.Secret;
import com.example.instagramjpa.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.instagramjpa.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class JwtService {


    @Autowired
    private final RedisService redisService;

    private String blackListATPrefix;

    /*
    RefreshToken 생성
    @param userId
    @return String 1년
     */
    public String createRefreshToken(Long userIdx) {
        Date now=new Date();

        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx)
                .setSubject("refreshToken")
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+ (1000 * 60 * 60 * 24 * 365)))
                .signWith(SignatureAlgorithm.HS256,Secret.JWT_SECRET_KEY)
                .compact();
    }
    /*
    AccessToken 생성
    @param userId 한달
    @return String
     */
    public String createJwt(Long userId){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId",userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+ (1000 * 60 * 60 )))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userId 추출
    @return int
    @throws BaseException
     */
    public Long getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();

        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        String expiredAt= redisService.getValues(accessToken);
        Long userId = claims.getBody().get("userId",Long.class);

        if(expiredAt==null){
            return userId;
        }
        if(expiredAt.equals(String.valueOf(userId))){
            throw new BaseException(HIJACK_ACCESS_TOKEN);
        }
        // 3. userId 추출
        return userId;
    }

    public Date getExpiredTime(String token){
        return Jwts.parser().setSigningKey(Secret.JWT_SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }


    public void logOut(Long userId, String accessToken) {
        long expiredAccessTokenTime=getExpiredTime(accessToken).getTime() - new Date().getTime();
        redisService.saveToken(accessToken,String.valueOf(userId),expiredAccessTokenTime);
        redisService.deleteValues(String.valueOf(userId));

    }
}
