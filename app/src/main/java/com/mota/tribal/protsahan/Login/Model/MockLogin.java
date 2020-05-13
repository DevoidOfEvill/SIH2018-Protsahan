package com.mota.tribal.protsahan.Login.Model;

import android.os.Handler;

import com.mota.tribal.protsahan.Login.LoginCallback;
import com.mota.tribal.protsahan.Login.Model.Data.UserInfo;


public class MockLogin implements LoginProvider {
    private UserInfo mockData;

    @Override
    public void getUser(String username, String password, final LoginCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(getMockData());
            }
        }, 500);
    }

    public UserInfo getMockData() {

        mockData = new UserInfo(true, "success", "123456789", "ayushjain17aug@gmail.com", "1234");
        return mockData;
    }
}
