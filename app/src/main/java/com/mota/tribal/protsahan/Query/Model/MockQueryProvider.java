package com.mota.tribal.protsahan.Query.Model;

import android.os.Handler;

import com.mota.tribal.protsahan.Query.Model.Data.Query;
import com.mota.tribal.protsahan.Query.Model.Data.QueryData;
import com.mota.tribal.protsahan.Query.QueryCallback;

import java.util.ArrayList;

public class MockQueryProvider implements QueryProvider {


    public QueryData getMockData() {
        ArrayList<Query> queries = new ArrayList<>();
        queries.add(new Query("101", "What are the skill improvement programs for weavers??", "The Ministry will be conducting workshops for weavers in the near future and you will be notified soon "));
        queries.add(new Query("101", "What are the skill improvement programs for weavers??", "The Ministry will be conducting workshops for weavers in the near future and you will be notified soon "));
        queries.add(new Query("101", "What are the skill improvement programs for weavers??", "The Ministry will be conducting workshops for weavers in the near future and you will be notified soon "));
        queries.add(new Query("101", "What are the skill improvement programs for weavers??", "The Ministry will be conducting workshops for weavers in the near future and you will be notified soon "));
        queries.add(new Query("101", "What are the skill improvement programs for weavers??", "The Ministry will be conducting workshops for weavers in the near future and you will be notified soon "));
        queries.add(new Query("101", "What are the skill improvement programs for weavers??", "The Ministry will be conducting workshops for weavers in the near future and you will be notified soon "));
        queries.add(new Query("101", "What are the skill improvement programs for weavers??", "The Ministry will be conducting workshops for weavers in the near future and you will be notified soon "));
        QueryData mockData = new QueryData(queries, true, "Aa gyi saari queries");
        return mockData;
    }

    @Override
    public void getAllQueries(String email, String password, final QueryCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(getMockData());
            }
        }, 500);

    }

    @Override
    public void askQuery(String username, String token, String question, final QueryCallback queryCallback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queryCallback.onSuccess(getMockPostData());
            }
        }, 500);
    }

    public QueryData getMockPostData() {
        QueryData mockPostData = new QueryData(null, true,
                "Query Posted Successfully\n You will be notified about the answer");
        return mockPostData;
    }
}
