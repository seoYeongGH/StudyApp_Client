package com.example.study.Retro;

import android.telecom.CallScreeningService;

import com.example.study.VO.UserVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/{server}/{doing}")
    Call<ResponseData> doService(@Path("server") String server, @Path("doing") String doing, @Body UserVO userVO);
}
