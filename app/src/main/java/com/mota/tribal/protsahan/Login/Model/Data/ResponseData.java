package com.mota.tribal.protsahan.Login.Model.Data;

public class ResponseData {

    private boolean success;
    private String message;

    public ResponseData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}