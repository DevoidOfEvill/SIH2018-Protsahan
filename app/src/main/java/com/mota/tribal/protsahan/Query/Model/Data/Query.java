package com.mota.tribal.protsahan.Query.Model.Data;


public class Query {
    private String _id, description, answer;

    public Query(String id, String description, String answer) {
        _id = id;
        this.description = description;
        this.answer = answer;
    }

    public String get_id() {
        return _id;
    }

    public String getQuestion() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }
}
