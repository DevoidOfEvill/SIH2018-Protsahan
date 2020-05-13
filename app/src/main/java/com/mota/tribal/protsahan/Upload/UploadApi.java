package com.mota.tribal.protsahan.Upload;

import com.mota.tribal.protsahan.Helper.Urls;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApi {

    @Multipart
    @POST(Urls.VIDEO_UPLOAD)
    Call<Data> uploadVideoToServer(@Header("Authorization") String authHeader, @Part("username") String username,
                                   @Part("title") String videoName, @Part MultipartBody.Part video);

    @Multipart
    @POST(Urls.IMG_UPLOAD)
    Call<Data> uploadImageToServer(@Header("Authorization") String authHeader, @Part("username") String username,
                                   @Part("title") String imageName, @Part MultipartBody.Part image);

    @Multipart
    @POST(Urls.DOC_UPLOAD)
    Call<Data> uploadDocToServer(@Header("Authorization") String authHeader, @Part("username") String username,
                                 @Part("title") String docName, @Part MultipartBody.Part doc);

}
