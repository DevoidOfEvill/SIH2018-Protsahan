package com.mota.tribal.protsahan.Login.Presenter;

import android.content.Context;
import android.util.Log;

import com.mota.tribal.protsahan.Login.LoginCallback;
import com.mota.tribal.protsahan.Login.Model.Data.UserInfo;
import com.mota.tribal.protsahan.Login.Model.LoginProvider;
import com.mota.tribal.protsahan.Login.View.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginProvider provider;
    private LoginView view;
    private Context context;

    public LoginPresenterImpl(LoginProvider provider, LoginView view, Context context) {
        this.context = context;
        this.provider = provider;
        this.view = view;
    }

    @Override
    public void getResponse(String email, String password) {
        view.showProgressBar(true);
        provider.getUser(email, password, new LoginCallback() {
            @Override
            public void onSuccess(UserInfo body) {
                Log.d("Ayush", body.toString());
                view.showProgressBar(false);
                if (body.isSuccess()) {
                    view.showUserDetails(body);
                } else
                    view.showMessage(body.getMessage());
            }

            @Override
            public void onFailure() {
                view.showProgressBar(false);
                view.showMessage("Login Failed.Try again later!!!");
            }
        });
    }
}

