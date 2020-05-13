package com.mota.tribal.protsahan.Query.View;

import com.mota.tribal.protsahan.Query.Model.Data.QueryData;

public interface QueryView {

    void showProgressBar(boolean b);

    void showMessage(String message);

    void onGettingAllQueries(QueryData data);


}
