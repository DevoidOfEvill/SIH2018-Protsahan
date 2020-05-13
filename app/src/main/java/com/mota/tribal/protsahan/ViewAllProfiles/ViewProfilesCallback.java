package com.mota.tribal.protsahan.ViewAllProfiles;

import com.mota.tribal.protsahan.ViewAllProfiles.Model.Data.ViewProfilesData;


public interface ViewProfilesCallback {
    void onSuccess(ViewProfilesData body);

    void onFailure();
}
