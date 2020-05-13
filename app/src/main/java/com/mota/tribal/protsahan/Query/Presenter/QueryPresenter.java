package com.mota.tribal.protsahan.Query.Presenter;

public interface QueryPresenter {
    void getAllQueries(String id, String token);

    void askQuery(String id, String token, String question);
}
