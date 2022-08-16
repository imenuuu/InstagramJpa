package com.example.instagramjpa.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserRes {
    @ApiModelProperty(
            name="userId"
            ,example = "4"
    )
    @ApiParam(value = "사용자 Id")
    private Long userId;
    @ApiParam(value = "액세스 토큰")
    private String accessToken;
    @ApiParam(value = "리프레쉬 토큰")
    private String refreshToken;
}
