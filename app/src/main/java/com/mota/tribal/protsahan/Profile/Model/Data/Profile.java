package com.mota.tribal.protsahan.Profile.Model.Data;


import java.util.ArrayList;

public class Profile {


    private String token, name, description, tribe, address, aadharNO, phone, gender, profilephoto, username, state, qualification;
    private ArrayList<String> skills;

    public Profile(String token, String name, String description,
                   String tribe, String address, String aadharNO,
                   String phone, String gender, String profilephoto, String username, String state,
                   ArrayList<String> skills, String qualification) {
        this.token = token;
        this.name = name;
        this.description = description;
        this.tribe = tribe;
        this.address = address;
        this.aadharNO = aadharNO;
        this.phone = phone;
        this.gender = gender;
        this.profilephoto = profilephoto;
        this.username = username;
        this.state = state;
        this.qualification = qualification;
        this.skills = skills;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTribe() {
        return tribe;
    }

    public String getAddress() {
        return address;
    }

    public String getAadhar() {
        return aadharNO;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getImg() {
        return profilephoto;
    }

    public String getUsername() {
        return username;
    }

    public String getState() {
        return state;
    }

    public ArrayList<String> getskills() {
        return skills;
    }

    public String getqualification() {
        return qualification;
    }
}
