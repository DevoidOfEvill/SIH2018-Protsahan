package com.mota.tribal.protsahan.Schemes.Model.Data;

public class InnerData {

    public final String title;
    public final String name;
    public final int year;
    public final String description;
    public final String avatarUrl;

    public InnerData(String title, String name, String description, String avatarUrl, int year) {
        this.title = title;
        this.name = name;
        this.year = year;
        this.avatarUrl = avatarUrl;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getDescription() {
        return description;
    }

}
