package com.mota.tribal.protsahan.Login.Api;

import com.mota.tribal.protsahan.Login.Model.Data.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FCM_Api {
    @FormUrlEncoded
    @POST("artisans/fcmtoken")
    Call<ResponseData> getResponse(@Field("username") String username, @Field("fcmtoken") String token);


}
