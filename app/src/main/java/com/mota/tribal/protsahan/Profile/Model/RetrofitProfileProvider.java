package com.mota.tribal.protsahan.Profile.Model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Profile.Api.ProfileApi;
import com.mota.tribal.protsahan.Profile.DeleteCallback;
import com.mota.tribal.protsahan.Profile.Model.Data.DeleteData;
import com.mota.tribal.protsahan.Profile.Model.Data.Profile;
import com.mota.tribal.protsahan.Profile.Model.Data.ProfileData;
import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;
import com.mota.tribal.protsahan.Profile.ProfileCallback;
import com.mota.tribal.protsahan.Profile.VidImgDocCallback;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitProfileProvider implements ProfileProvider {

    private final Retrofit retrofit;
    private ProfileApi api;

    public RetrofitProfileProvider() {
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
    public void getProfile(String token, String username, final ProfileCallback callback) {
        api = retrofit.create(ProfileApi.class);
        token = "Bearer " + token;
        Call<ProfileData> call = api.getProfile(username, token);
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    @Override
    public void postProfile(Profile profile, final ProfileCallback callback) {

        api = retrofit.create(ProfileApi.class);
        Call<ProfileData> call = api.postProfile(profile.getUsername(), "Bearer " + profile.getToken(), profile.getName(),
                profile.getDescription(), profile.getTribe(), profile.getAddress(), profile.getAadhar(),
                profile.getPhone(), profile.getGender(), profile.getState(), profile.getskills(), profile.getqualification());
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });

    }

    @Override
    public void getVideos(String token, String username, final VidImgDocCallback callback) {
        api = retrofit.create(ProfileApi.class);
        token = "Bearer " + token;
        Call<VidImgDocData> call = api.getVideos(token, username);
        call.enqueue(new Callback<VidImgDocData>() {
            @Override
            public void onResponse(Call<VidImgDocData> call, Response<VidImgDocData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VidImgDocData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    @Override
    public void getImages(String token, String username, final VidImgDocCallback callback) {
        api = retrofit.create(ProfileApi.class);
        token = "Bearer " + token;
        Call<VidImgDocData> call = api.getImages(token, username);
        call.enqueue(new Callback<VidImgDocData>() {
            @Override
            public void onResponse(Call<VidImgDocData> call, Response<VidImgDocData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VidImgDocData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    @Override
    public void getDocs(String token, String username, final VidImgDocCallback callback) {
        token = "Bearer " + token;
        api = retrofit.create(ProfileApi.class);
        Call<VidImgDocData> call = api.getDocuments(token, username);
        call.enqueue(new Callback<VidImgDocData>() {
            @Override
            public void onResponse(Call<VidImgDocData> call, Response<VidImgDocData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VidImgDocData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    @Override
    public void postProfilePic(String token, String username, MultipartBody.Part file, final ProfileCallback callback) {
        api = retrofit.create(ProfileApi.class);
        token = "Bearer " + token;
        Call<ProfileData> call = api.postProfilePic(token, username, file);
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    @Override
    public void deleteImage(String token, String username, String _id, String imageTitle, final DeleteCallback callback) {
        api = retrofit.create(ProfileApi.class);
        token = "Bearer " + token;
        Call<DeleteData> call = api.deleteImage(token, username, _id, imageTitle);
        call.enqueue(new Callback<DeleteData>() {
            @Override
            public void onResponse(Call<DeleteData> call, Response<DeleteData> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DeleteData> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void deleteVideo(String token, String username, String _id, String videoTitle, final DeleteCallback callback) {
        api = retrofit.create(ProfileApi.class);
        Log.d("abhi", username + " " + _id + "abhi");
        token = "Bearer " + token;
        Call<DeleteData> call = api.deleteVideo(token, username, _id, videoTitle);
        call.enqueue(new Callback<DeleteData>() {
            @Override
            public void onResponse(Call<DeleteData> call, Response<DeleteData> response) {
                callback.onSuccess(response.body());
            }
            @Override
            public void onFailure(Call<DeleteData> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}