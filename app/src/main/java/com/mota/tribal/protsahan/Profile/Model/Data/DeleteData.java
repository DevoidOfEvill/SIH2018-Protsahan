package com.mota.tribal.protsahan.Profile.Model.Data;


import java.util.ArrayList;

public class DeleteData {
    private boolean success;
    private Org org;

    public DeleteData(boolean success, Org org) {
        this.success = success;
        this.org = org;
    }

    public boolean isSuccess() {
        return success;
    }

    public Org getDocs() {
        return org;
    }


    public static class Org {
        private ArrayList<VidImgDocData.Obj> videos, images;

        public Org(ArrayList<VidImgDocData.Obj> videos, ArrayList<VidImgDocData.Obj> images) {
            this.videos = videos;
            this.images = images;
        }

        public ArrayList<VidImgDocData.Obj> getVideos() {
            return videos;
        }

        public ArrayList<VidImgDocData.Obj> getImages() {
            return images;
        }
    }

}
