package com.example.study.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserVO {
    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("email")
    @Expose
    String email;

    public UserVO(){ }

    public UserVO(String name, String id, String password, String email) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
