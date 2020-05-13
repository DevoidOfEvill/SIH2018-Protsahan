package com.mota.tribal.protsahan.Query.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Query.Api.QueryApi;
import com.mota.tribal.protsahan.Query.Model.Data.QueryData;
import com.mota.tribal.protsahan.Query.QueryCallback;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitQueryProvider implements QueryProvider {
    private final Retrofit retrofit;
    private QueryApi retrofitApi;

    public RetrofitQueryProvider() {
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
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }


    @Override
    public void getAllQueries(String id, String token, final QueryCallback callback) {
        retrofitApi = retrofit.create(QueryApi.class);
        token = "Bearer " + token;
        Call<QueryData> call = retrofitApi.getUserDetails(id, token);
        call.enqueue(new Callback<QueryData>() {

            @Override
            public void onResponse(Call<QueryData> call, Response<QueryData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<QueryData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    @Override
    public void askQuery(String id, String token, String question, final QueryCallback queryCallback) {
        retrofitApi = retrofit.create(QueryApi.class);
        token = "Bearer " + token;
        Call<QueryData> call = retrofitApi.askQuery(id, token, question);
        call.enqueue(new Callback<QueryData>() {
            @Override
            public void onResponse(Call<QueryData> call, Response<QueryData> response) {
                queryCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<QueryData> call, Throwable t) {
                t.printStackTrace();
                queryCallback.onFailure();
            }
        });
    }
}