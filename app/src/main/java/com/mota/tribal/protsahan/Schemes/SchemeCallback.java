package com.mota.tribal.protsahan.Schemes;

import com.mota.tribal.protsahan.Schemes.Model.Data.SchemeInfo;

public interface SchemeCallback {
    void onSuccess(SchemeInfo data);

    void onFailure();
}
