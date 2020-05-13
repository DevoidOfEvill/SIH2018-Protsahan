package com.mota.tribal.protsahan.ViewAllProfiles.Model.Data;

import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;

import java.util.ArrayList;

public class ViewProfilesData {

    private ArrayList<Docs> docs;

    public ViewProfilesData(ArrayList<Docs> docs) {
        this.docs = docs;
    }

    public ArrayList<Docs> getDocs() {
        return docs;
    }

    public static class Docs {
        private ArrayList<VidImgDocData.Obj> videos, images;
        private String username, _id, name, profilephoto;

        public Docs(ArrayList<VidImgDocData.Obj> videos, ArrayList<VidImgDocData.Obj> images,
                    String username, String id, String name, String profilephoto) {
            this.videos = videos;
            this.images = images;
            this.username = username;
            _id = id;
            this.name = name;
            this.profilephoto = profilephoto;
        }

        public ArrayList<VidImgDocData.Obj> getVideos() {
            return videos;
        }

        public ArrayList<VidImgDocData.Obj> getImages() {
            return images;
        }

        public String getUsername() {
            return username;
        }

        public String get_id() {
            return _id;
        }

        public String getName() {
            return name;
        }

        public String getProfilePicUrl() {
            return profilephoto;
        }
    }
}
