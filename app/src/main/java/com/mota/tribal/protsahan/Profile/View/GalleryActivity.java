package com.mota.tribal.protsahan.Profile.View;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.mota.tribal.protsahan.Profile.Model.Data.Profile;
import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;
import com.mota.tribal.protsahan.Profile.Model.RetrofitProfileProvider;
import com.mota.tribal.protsahan.Profile.Presenter.ProfilePresenter;
import com.mota.tribal.protsahan.Profile.Presenter.ProfilePresenterImpl;
import com.mota.tribal.protsahan.R;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements ProfileView {

    private RecyclerView recyclerView;
    private ProfilePresenter presenter;
    private String tribalName, profilePic, type, count_type;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progress_bar);

        Bundle data = getIntent().getExtras();
        tribalName = data.getString("tribal_name");
        ArrayList<VidImgDocData.Obj> urlList;
        urlList = (ArrayList<VidImgDocData.Obj>) data.getSerializable("objects");

//        Log.d("abhi",urlList.size()+" "+urlList.get(0).getUrl());

        profilePic = data.getString("profile_pic_url");
        type = data.getString("type");
        count_type = data.getString("count_type");
        presenter = new ProfilePresenterImpl(this, new RetrofitProfileProvider(), this);

        GalleryAdapter adapter = new GalleryAdapter(urlList, tribalName,
                profilePic, type, count_type, this, presenter);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(R.id.image_gallery_rel_layout), message, Snackbar.LENGTH_LONG).
                setAction("Action", null).show();
    }

    @Override
    public void showProgressBar(boolean b) {
        if (b)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onProfilePosted() {

    }

    @Override
    public void onProfileGet(Profile profile) {

    }

    @Override
    public void showVideos(ArrayList<VidImgDocData.Obj> urls) {
        GalleryAdapter adapter = new GalleryAdapter(urls, tribalName,
                profilePic, type, count_type, this, presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showImages(ArrayList<VidImgDocData.Obj> urls) {
        GalleryAdapter adapter = new GalleryAdapter(urls, tribalName,
                profilePic, type, count_type, this, presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showDocs(ArrayList<VidImgDocData.Obj> urls) {

    }
}