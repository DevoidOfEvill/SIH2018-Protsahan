package com.mota.tribal.protsahan.ViewAllProfiles.Api;

import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.Data.ViewProfilesData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ViewProfilesApi {

    @GET(Urls.GET_ALL_PROFILES)
    Call<ViewProfilesData> getProfiles();

}
