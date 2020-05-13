package com.mota.tribal.protsahan.Schemes.Api;

import com.mota.tribal.protsahan.Schemes.Model.Data.SchemeInfo;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SchemeApi {

    @FormUrlEncoded
    @POST("artisans/login")
    Call<SchemeInfo> getSchemes();


}

