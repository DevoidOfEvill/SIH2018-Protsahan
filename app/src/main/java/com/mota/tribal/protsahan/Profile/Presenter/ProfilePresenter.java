package com.mota.tribal.protsahan.Profile.Presenter;

import com.mota.tribal.protsahan.Profile.Model.Data.Profile;

import okhttp3.MultipartBody;


public interface ProfilePresenter {
    void getProfile(String id, String username);

    void postProfile(Profile profile);

    void getVideos(String id, String username);

    void getImages(String id, String username);

    void getDocs(String id, String username);

    void postProfilePic(String id, String username, MultipartBody.Part file);

    void deleteImage(String token, String username, String url, String imageTitle);

    void deleteVideo(String token, String username, String url, String videoTitle);
}
