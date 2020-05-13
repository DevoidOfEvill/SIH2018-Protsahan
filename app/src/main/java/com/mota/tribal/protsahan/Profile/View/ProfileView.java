package com.mota.tribal.protsahan.Profile.View;

import com.mota.tribal.protsahan.Profile.Model.Data.Profile;
import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;

import java.util.ArrayList;

public interface ProfileView {


    void showMessage(String message);


    void showProgressBar(boolean b);

    void onProfilePosted();

    void onProfileGet(Profile profile);

    void showVideos(ArrayList<VidImgDocData.Obj> urls);

    void showImages(ArrayList<VidImgDocData.Obj> urls);

    void showDocs(ArrayList<VidImgDocData.Obj> urls);

}
