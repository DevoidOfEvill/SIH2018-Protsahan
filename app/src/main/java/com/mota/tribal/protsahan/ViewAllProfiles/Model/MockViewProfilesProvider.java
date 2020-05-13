package com.mota.tribal.protsahan.ViewAllProfiles.Model;

import android.os.Handler;

import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.Data.ViewProfilesData;
import com.mota.tribal.protsahan.ViewAllProfiles.ViewProfilesCallback;

import java.util.ArrayList;

public class MockViewProfilesProvider implements ViewProfilesProvider {


    @Override
    public void getProfiles(final ViewProfilesCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(getMockData1());
            }
        }, 500);
    }

    public ViewProfilesData getMockData1() {
        ViewProfilesData mockData1;
        ArrayList<VidImgDocData.Obj> images = new ArrayList<>();
        ArrayList<VidImgDocData.Obj> videos = new ArrayList<>();
        images.add(new VidImgDocData.Obj("https://jpeg.org/images/jpeg2000-home.jpg", "image 1", "101", true));
        images.add(new VidImgDocData.Obj("https://jpeg.org/images/jpeg2000-home.jpg", "image 1", "101", true));
        images.add(new VidImgDocData.Obj("https://jpeg.org/images/jpeg2000-home.jpg", "image 1", "101", true));
        images.add(new VidImgDocData.Obj("https://jpeg.org/images/jpeg2000-home.jpg", "image 1", "101", true));
        images.add(new VidImgDocData.Obj("https://jpeg.org/images/jpeg2000-home.jpg", "image 1", "101", true));
        images.add(new VidImgDocData.Obj("https://jpeg.org/images/jpeg2000-home.jpg", "image 1", "101", true));
        images.add(new VidImgDocData.Obj("https://jpeg.org/images/jpeg2000-home.jpg", "image 1", "101", true));

        videos.add(new VidImgDocData.Obj("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp", "video 1", "101", true));
        videos.add(new VidImgDocData.Obj("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp", "video 1", "101", true));
        videos.add(new VidImgDocData.Obj("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp", "video 1", "101", true));
        videos.add(new VidImgDocData.Obj("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp", "video 1", "101", true));
        videos.add(new VidImgDocData.Obj("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp", "video 1", "101", true));
        videos.add(new VidImgDocData.Obj("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp", "video 1", "101", true));
        videos.add(new VidImgDocData.Obj("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp", "video 1", "101", true));

        ViewProfilesData.Docs doc = new ViewProfilesData.Docs(videos, images, "ankit.son.og@gmail.com",
                "101", "Ayush", "https://jpeg.org/images/jpeg2000-home.jpg");
        ArrayList<ViewProfilesData.Docs> docs = new ArrayList<>();
        docs.add(doc);

        mockData1 = new ViewProfilesData(docs);

        return mockData1;
    }
}
