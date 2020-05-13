package com.mota.tribal.protsahan.Login.Api;

import com.mota.tribal.protsahan.Login.Model.Data.UserInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("artisans/login")
    Call<UserInfo> getUserDetails(@Field("username") String username, @Field("password") String password);
}

