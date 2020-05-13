package com.mota.tribal.protsahan.Query.Model;

import com.mota.tribal.protsahan.Query.QueryCallback;


public interface QueryProvider {
    void getAllQueries(String email, String password, QueryCallback callback);

    void askQuery(String username, String token, String question, QueryCallback queryCallback);
}
