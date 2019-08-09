package com.example.study.Retro;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LogoutService {
    @GET("/loginServer/logout")
    Call<ResponseData> logoutService();
}
