package com.mota.tribal.protsahan.Query;

import com.mota.tribal.protsahan.Query.Model.Data.QueryData;

public interface QueryCallback {
    void onSuccess(QueryData data);

    void onFailure();
}
