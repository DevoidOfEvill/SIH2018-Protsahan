package com.mota.tribal.protsahan.Login;

import com.mota.tribal.protsahan.Login.Model.Data.UserInfo;

public interface LoginCallback {
    void onSuccess(UserInfo data);

    void onFailure();
}
