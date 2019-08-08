package com.example.study.Retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("code")
    @Expose
    Integer code;

    @SerializedName("data")
    @Expose
    String data;

    public ResponseData(){}

    public ResponseData(Integer code){
        this.code = code;
    }

    public ResponseData(String data){
        this.data = data;
    }

    public ResponseData(Integer code, String data){
        this.code = code;
        this.data = data;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCode(){
        return code;
    }

    public String getData() {
        return data;
    }
}
