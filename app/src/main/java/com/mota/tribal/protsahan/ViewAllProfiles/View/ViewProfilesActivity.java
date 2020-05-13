package com.mota.tribal.protsahan.ViewAllProfiles.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.mota.tribal.protsahan.Helper.BottomNavigationViewHelper;
import com.mota.tribal.protsahan.Login.View.AccountActivity;
import com.mota.tribal.protsahan.Login.View.SessionManager;
import com.mota.tribal.protsahan.MainActivity;
import com.mota.tribal.protsahan.Profile.View.ProfileActivity;
import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.Schemes.View.SchemeActivity;
import com.mota.tribal.protsahan.Upload.UploadActivity;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.Data.ViewProfilesData;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.RetrofitViewProfilesProvider;
import com.mota.tribal.protsahan.ViewAllProfiles.Presenter.ViewProfilesPresenter;
import com.mota.tribal.protsahan.ViewAllProfiles.Presenter.ViewProfilesPresenterImpl;

import java.util.ArrayList;

public class ViewProfilesActivity extends AppCompatActivity implements ViewProfilesView {

    private RecyclerView videoRecycler, imageRecycler;
    private ProgressBar progressBar;


    private Intent intent;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(ViewProfilesActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_scheme:
                    intent = new Intent(ViewProfilesActivity.this, SchemeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.navigation_profiles:
                    break;
                case R.id.navigation_account:
                    if (sessionManager.isLoggedIn()) {
                        intent = new Intent(ViewProfilesActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        intent = new Intent(ViewProfilesActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.navigation_settings:
                    if (sessionManager.isLoggedIn()) {
                        intent = new Intent(ViewProfilesActivity.this, UploadActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar.make(findViewById(R.id.relative_layout), "Login to post queries!",
                                Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ViewProfilesActivity.this, AccountActivity.class);
                                startActivity(intent);
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorWhite)).show();
                    }
                    break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profiles);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.navigation_profiles);

        imageRecycler = findViewById(R.id.image_recycler);
        videoRecycler = findViewById(R.id.video_recycler);
        progressBar = findViewById(R.id.progress_bar);

        imageRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(imageRecycler);

        videoRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.START);
        snapHelperTop.attachToRecyclerView(imageRecycler);

        ViewProfilesPresenter presenter = new ViewProfilesPresenterImpl(new RetrofitViewProfilesProvider(), this, this);
        Log.d("abhi", "in QueryView Profiles Activity");
        presenter.getProfiles();

    }

    @Override
    public void showProgressBar(boolean b) {
        if (b)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(R.id.relative_layout), message, Snackbar.LENGTH_LONG).
                setAction("Action", null).show();
    }

    @Override
    public void showResult(ArrayList<ViewProfilesData.Docs> docs) {
        RecyclerViewAdapter imageAdapter = new RecyclerViewAdapter(this, docs, "image");
        RecyclerViewAdapter videoAdapter = new RecyclerViewAdapter(this, docs, "video");
        imageRecycler.setAdapter(imageAdapter);
        videoRecycler.setAdapter(videoAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewProfilesActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
