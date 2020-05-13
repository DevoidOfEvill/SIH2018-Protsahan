package com.mota.tribal.protsahan.Query.Model.Data;

import java.util.ArrayList;

public class QueryData {
    private ArrayList<Query> queries;
    private boolean success;
    private String message;

    public QueryData(ArrayList<Query> queries, boolean success, String message) {
        this.queries = queries;
        this.success = success;
        this.message = message;
    }

    public ArrayList<Query> getQueries() {
        return queries;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

