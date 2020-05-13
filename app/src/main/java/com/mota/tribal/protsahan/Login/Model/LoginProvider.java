package com.mota.tribal.protsahan.Login.Model;

import com.mota.tribal.protsahan.Login.LoginCallback;


public interface LoginProvider {
    void getUser(String email, String password, LoginCallback callback);
}

