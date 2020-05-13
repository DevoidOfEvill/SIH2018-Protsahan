package com.mota.tribal.protsahan.ViewAllProfiles.Presenter;

import android.content.Context;
import android.util.Log;

import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.Data.ViewProfilesData;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.ViewProfilesProvider;
import com.mota.tribal.protsahan.ViewAllProfiles.View.ViewProfilesView;
import com.mota.tribal.protsahan.ViewAllProfiles.ViewProfilesCallback;

public class ViewProfilesPresenterImpl implements ViewProfilesPresenter {

    private ViewProfilesProvider provider;
    private ViewProfilesView view;
    private Context context;

    public ViewProfilesPresenterImpl(ViewProfilesProvider provider, ViewProfilesView view, Context context) {
        this.provider = provider;
        this.view = view;
        this.context = context;
    }

    @Override
    public void getProfiles() {
        view.showProgressBar(true);
        provider.getProfiles(new ViewProfilesCallback() {
            @Override
            public void onSuccess(ViewProfilesData body) {
                view.showProgressBar(false);
                Log.d("abhi", "count " + body.getDocs().get(0).getVideos().size());
                view.showResult(body.getDocs());
            }

            @Override
            public void onFailure() {
                view.showProgressBar(false);
                view.showMessage(context.getString(R.string.ConnectionError));

            }
        });
    }

}
