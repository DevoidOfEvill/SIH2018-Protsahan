package com.mota.tribal.protsahan.Profile;

import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;


public interface VidImgDocCallback {
    void onSuccess(VidImgDocData body);

    void onFailure();
}
