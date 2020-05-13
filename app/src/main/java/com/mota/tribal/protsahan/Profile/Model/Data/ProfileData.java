package com.mota.tribal.protsahan.Profile.Model.Data;

public class ProfileData {
    private boolean success;
    private String message;
    private Profile docs;

    public ProfileData(boolean success, String message, Profile docs) {
        this.success = success;
        this.message = message;
        this.docs = docs;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Profile getProfile() {
        return docs;
    }
}
