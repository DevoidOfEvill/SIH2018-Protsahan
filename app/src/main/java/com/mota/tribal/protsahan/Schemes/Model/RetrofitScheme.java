package com.mota.tribal.protsahan.Schemes.Model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mota.tribal.protsahan.Schemes.Api.SchemeApi;
import com.mota.tribal.protsahan.Schemes.Model.Data.SchemeInfo;
import com.mota.tribal.protsahan.Schemes.SchemeCallback;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitScheme implements SchemeProvider {
    private final Retrofit retrofit;
    private SchemeApi schemeApi;

    public RetrofitScheme() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(15000, java.util.concurrent.TimeUnit.SECONDS).
                readTimeout(15000, java.util.concurrent.TimeUnit.SECONDS).
                writeTimeout(15000, java.util.concurrent.TimeUnit.SECONDS).
                addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:3000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }


    @Override
    public void getSchemes(final SchemeCallback callback) {
        schemeApi = retrofit.create(SchemeApi.class);
        Call<SchemeInfo> call = schemeApi.getSchemes();
        call.enqueue(new Callback<SchemeInfo>() {

            @Override
            public void onResponse(Call<SchemeInfo> call, Response<SchemeInfo> response) {
                Log.d("Ayush", response.toString());
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SchemeInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }
}