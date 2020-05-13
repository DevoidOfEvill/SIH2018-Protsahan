package com.mota.tribal.protsahan.Profile;

import com.mota.tribal.protsahan.Profile.Model.Data.ProfileData;


public interface ProfileCallback {
    void onSuccess(ProfileData body);

    void onFailure();
}
