package com.mota.tribal.protsahan.Upload;

public class Data {

    private boolean success;
    private String message;

    public Data(boolean success, String message) {
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