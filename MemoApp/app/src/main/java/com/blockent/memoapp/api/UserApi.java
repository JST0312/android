package com.blockent.memoapp.api;

import com.blockent.memoapp.model.User;
import com.blockent.memoapp.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

// 유저 관련 API 들을 모아놓은 인터페이스
public interface UserApi {

    // 회원가입 API 함수 작성
    @POST("/user/register")
    Call<UserRes> register(@Body User user);

    // 로그인 API
    @POST("/user/login")
    Call<UserRes> login(@Body User user);

    // 로그아웃 API
    @POST("/user/logout")
    Call<UserRes> logout(@Header("Authorization") String token);

}
