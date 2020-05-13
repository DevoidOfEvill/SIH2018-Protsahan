package com.mota.tribal.protsahan.Schemes.Presenter;

import android.content.Context;

import com.mota.tribal.protsahan.Schemes.Model.Data.SchemeInfo;
import com.mota.tribal.protsahan.Schemes.Model.SchemeProvider;
import com.mota.tribal.protsahan.Schemes.SchemeCallback;
import com.mota.tribal.protsahan.Schemes.View.SchemeView;

public class SchemePresenterImpl implements SchemePresenter {

    private SchemeProvider provider;
    private SchemeView view;
    private Context context;

    public SchemePresenterImpl(SchemeProvider provider, SchemeView view, Context context) {
        this.context = context;
        this.provider = provider;
        this.view = view;
    }

    @Override
    public void getResponse() {
        view.showProgressBar(true);
        provider.getSchemes(new SchemeCallback() {
            @Override
            public void onSuccess(SchemeInfo body) {
                view.showProgressBar(false);
                if (SchemeInfo.isSuccess()) {
                    view.getSchemes(body);
                } else
                    view.showMessage(SchemeInfo.getMessage());
            }

            @Override
            public void onFailure() {
                view.showProgressBar(false);
                view.showMessage("Login Failed.Try again later!!!");
            }
        });
    }
}

