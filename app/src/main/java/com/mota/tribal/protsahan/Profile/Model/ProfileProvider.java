
package com.mota.tribal.protsahan.Profile.Model;

import com.mota.tribal.protsahan.Profile.DeleteCallback;
import com.mota.tribal.protsahan.Profile.Model.Data.Profile;
import com.mota.tribal.protsahan.Profile.ProfileCallback;
import com.mota.tribal.protsahan.Profile.VidImgDocCallback;

import okhttp3.MultipartBody;


public interface ProfileProvider {
    void getProfile(String token, String username, ProfileCallback callback);

    void postProfile(Profile profile, ProfileCallback callback);

    void getVideos(String id, String username, VidImgDocCallback callback);

    void getImages(String id, String username, VidImgDocCallback callback);

    void getDocs(String id, String username, VidImgDocCallback callback);

    void postProfilePic(String token, String username, MultipartBody.Part file, ProfileCallback callback);

    void deleteImage(String token, String username, String url, String imageTitle, DeleteCallback callback);

    void deleteVideo(String token, String username, String url, String videoTitle, DeleteCallback deleteCallback);
}
