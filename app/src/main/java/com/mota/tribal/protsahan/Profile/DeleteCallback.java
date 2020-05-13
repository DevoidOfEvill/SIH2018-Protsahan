package com.mota.tribal.protsahan.Profile;

import com.mota.tribal.protsahan.Profile.Model.Data.DeleteData;

public interface DeleteCallback {
    void onSuccess(DeleteData body);

    void onFailure();
}
