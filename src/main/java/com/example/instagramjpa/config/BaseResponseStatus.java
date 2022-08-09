package com.example.instagramjpa.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    NOT_PUBLIC_USER(false,2004,"비공개 유저입니다. 다른 URI 로 요청해주세요"),

    // users
    USERS_EMPTY_userId(false, 2010, "유저 아이디 값을 확인해주세요."),
    NOT_EXIST_USER(false,2011,"존재하지 않는 유저 입니다"),
    BLOCKED_BY_PROFILE_USER(false,2022,"차단 되었으므로 회원정보를 조회 할 수 없습니다."),

    // [POST] /users
    POST_USERS_EMPTY_ID(false, 2015, "아이디를 입력해주세요."),
    POST_USERS_INVALID_ID(false, 2016, "아이디 형식을 확인해주세요."),
    POST_USERS_EXISTS_ID(false,2017,"중복된 아이디 입니다."),
    NOT_EXIST_KAKAO_USER(false,2018,"등록된 카카오 유저가 아닙니다 추가정보를 입력해주세요."),
    POST_USERS_INVALID_PHONE(false,2019,"전화번호 형식을 학인해주세요"),
    LONG_USER_ID_CHARACTERS(false,2040,"아이디 20자 미만으로 입력해주세요"),
    POST_USERS_INVALID_STRING(false,2041,"아이디에 문자열 하나 이상 포함해주세요"),


    NOT_SUCCESS_USER_INFO(false,2020,"계정 정보와 일치 하지 않음"),


    INVALID_USER_ACCESS(false,2021,"권한이 없습니다"),
    NOT_EXIST_FOLLOW(false,2022,"팔로우 요청이 존재하지 않습니다."),

    LONG_NUMBER_CHARACTERS(false,2023,"글자수 1000자 미만으로 입력해주세요"),
    MANY_PHOTO_BOARD(false,2024,"사진을 10장이하로 게시해주세요"),

    POST_COMMENT_EMPTY(false,2030,"댓글내용을 입력해주세요"),
    LONG_COMMENT_CHARACTER(false,2031,"200자 이하로 입력해주세요"),
    NOT_EXIST_BOARD(false,2032,"존재 하지 않는 게시물입니다."),
    NOT_EXIST_COMMENT(false,2033,"존재 하지 않는 댓글입니다."),
    NOT_EXIST_RECOMMENT(false,2034,"존재 하지 않는 대댓글입니다."),
    NOT_EXIST_RECOMMENT_LIKE(false,2035,"존재하지 않는 대댓글 좋아요 입니다,"),
    NOT_EXIST_COMMENT_LIKE(false,2036,"존재하지 않는 댓글 좋아요 입니다."),
    POST_USERS_PHONE_NUMBER(false, 2037,"이미 존재하는 전화번호입니다."),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),




    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userId}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패 하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패 하였습니다."),
    CANT_REPORT_BOARD(false,2090 ,"자신의 게시물은 신고할 수 없습니다." ),
    CANT_REPORT_COMMENT(false,2091 ,"자신의 댓글은 신고할 수 없습니다." ),
    NOT_DELETE_INVALID_USER(false, 2050,"삭제할 권한이 없습니다" ),
    MY_PROFILE_USER(false, 2060 ,"내 프로필 조회 API로 요청해주세요" ),
    EXIST_FOLLOW_REQUEST(false, 2061  , "이미 팔로우 요청이 존재합니다." ),
    NOT_EXIST_FILTER(false,2092 ,"필터를 제대로 입력해 주세요 1,2,3번만 존재합니다" ),
    NOT_EXIST_REPORT(false,2093 , "존재 하지 않는 신고번호 입니다."),
    EXIST_BLOCK_USER(false,2094 ,"해당 유저를 이미 차단하였습니다" );


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
