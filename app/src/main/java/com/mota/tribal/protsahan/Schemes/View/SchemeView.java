package com.mota.tribal.protsahan.Schemes.View;

import com.mota.tribal.protsahan.Schemes.Model.Data.SchemeInfo;

public interface SchemeView {

    void showProgressBar(boolean b);

    void showMessage(String message);

    void getSchemes(SchemeInfo data);
}
