package com.mota.tribal.protsahan.Schemes.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ProgressBar;

import com.mota.tribal.protsahan.MainActivity;
import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.Schemes.Model.Data.InnerData;
import com.mota.tribal.protsahan.Schemes.Model.Data.SchemeInfo;
import com.mota.tribal.protsahan.Schemes.Model.MockScheme;
import com.mota.tribal.protsahan.Schemes.Presenter.SchemePresenter;
import com.mota.tribal.protsahan.Schemes.Presenter.SchemePresenterImpl;
import com.mota.tribal.protsahan.Schemes.View.inner.InnerItem;
import com.mota.tribal.protsahan.Schemes.View.outer.OuterAdapter;
import com.ramotion.garlandview.TailLayoutManager;
import com.ramotion.garlandview.TailRecyclerView;
import com.ramotion.garlandview.TailSnapHelper;
import com.ramotion.garlandview.header.HeaderTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


public class SchemeActivity extends AppCompatActivity implements SchemeView, ViewClickHelperApp.ReadyListener {

    private SchemePresenter presenter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
        progressBar = findViewById(R.id.progress_bar);
        presenter = new SchemePresenterImpl(new MockScheme(), SchemeActivity.this, SchemeActivity.this);
        presenter.getResponse();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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
        Snackbar.make(findViewById(R.id.login), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void getSchemes(SchemeInfo data) {
        final List<List<InnerData>> outerData = data.innerData;
        final List<String> scehemename = data.getSchemename();
        initRecyclerView(outerData, scehemename);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView(List<List<InnerData>> data, List<String> schemename) {
        findViewById(R.id.progressBar).setVisibility(View.GONE);

        final TailRecyclerView rv = findViewById(R.id.recycler_view);
        ((TailLayoutManager) rv.getLayoutManager()).setPageTransformer(new HeaderTransformer());
        rv.setAdapter(new OuterAdapter(data, schemename, getApplicationContext()));

        new TailSnapHelper().attachToRecyclerView(rv);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnInnerItemClick(InnerItem item) {
        final InnerData itemData = item.getItemData();
        if (itemData == null) {
            return;
        }

        Intent intent = new Intent(SchemeActivity.this, SchemeDescriptionActivity.class);
        intent.putExtra("name", itemData.getName());
        intent.putExtra("description", itemData.getDescription());
        intent.putExtra("year", itemData.getYear() + "");
        intent.putExtra("imgurl", itemData.getAvatarUrl());

        startActivity(intent);
    }

    @Override
    public void onReady() {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SchemeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
