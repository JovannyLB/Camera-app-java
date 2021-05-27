package com.gjjg.camera_app_java.models;

import com.gjjg.camera_app_java.R;

public class Album {
    private long id;
    private String name;
    private String photos;
    private int image;

    public Album(long id, String name, String photos) {
        this.id = id;
        this.name = name;
        this.photos = photos;
        this.image = R.drawable.folder;
    }

    public Album(String name, String photos) {
        this.name = name;
        this.photos = photos;
        this.image = R.drawable.folder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public int getImage() {
        return image;
    }
}
