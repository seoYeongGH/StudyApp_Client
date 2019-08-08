package com.example.study.Retro;

import com.example.study.VO.WordVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WordService {
    @GET("/wordServer/{doing}")
    Call<List<WordVO>> getWords(@Path("doing") String doing);
}
