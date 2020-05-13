package com.mota.tribal.protsahan.Schemes.Model.Data;

import java.util.List;

public class SchemeInfo {
    public static boolean success;
    public static String message;
    public final List<String> schemename;
    public final List<List<InnerData>> innerData;

    public SchemeInfo(List<List<InnerData>> innerData, List<String> schemename, String message, boolean success) {
        this.innerData = innerData;
        SchemeInfo.message = message;
        SchemeInfo.success = success;
        this.schemename = schemename;
    }

    public static boolean isSuccess() {
        return success;
    }

    public static void setSuccess(boolean success) {
        SchemeInfo.success = success;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        SchemeInfo.message = message;
    }

    public List<List<InnerData>> getInnerData() {
        return innerData;
    }

    public List<String> getSchemename() {
        return schemename;
    }
}
