package com.mota.tribal.protsahan.Query.Api;

import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Query.Model.Data.QueryData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface QueryApi {

    @FormUrlEncoded
    @POST(Urls.ALL_QUERIES)
    Call<QueryData> getUserDetails(@Field("username") String username, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST(Urls.ASK_QUERY)
    Call<QueryData> askQuery(@Field("artisan_id") String username, @Header("Authorization") String token, @Field("query") String query);
}
