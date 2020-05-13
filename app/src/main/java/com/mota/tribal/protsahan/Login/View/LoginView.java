package com.mota.tribal.protsahan.Login.View;

import com.mota.tribal.protsahan.Login.Model.Data.UserInfo;

public interface LoginView {

    void showProgressBar(boolean b);

    void showMessage(String message);

    void showUserDetails(UserInfo data);
}
