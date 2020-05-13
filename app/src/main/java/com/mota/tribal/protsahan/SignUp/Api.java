package com.mota.tribal.protsahan.SignUp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {
    @FormUrlEncoded
    @POST("artisans")
    Call<Data> getResponse(@Field("username") String username, @Field("password") String password);
}
