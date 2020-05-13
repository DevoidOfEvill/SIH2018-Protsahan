package com.mota.tribal.protsahan.ViewAllProfiles.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.ViewAllProfiles.Api.ViewProfilesApi;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.Data.ViewProfilesData;
import com.mota.tribal.protsahan.ViewAllProfiles.ViewProfilesCallback;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitViewProfilesProvider implements ViewProfilesProvider {


    private final Retrofit retrofit;
    private ViewProfilesApi api;

    public RetrofitViewProfilesProvider() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(120, java.util.concurrent.TimeUnit.SECONDS).
                readTimeout(120, java.util.concurrent.TimeUnit.SECONDS).
                writeTimeout(120, java.util.concurrent.TimeUnit.SECONDS).
                addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Override
    public void getProfiles(final ViewProfilesCallback callback) {
        api = retrofit.create(ViewProfilesApi.class);
        Call<ViewProfilesData> call = api.getProfiles();
        call.enqueue(new Callback<ViewProfilesData>() {
            @Override
            public void onResponse(Call<ViewProfilesData> call, Response<ViewProfilesData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ViewProfilesData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }
}
