package com.gjjg.camera_app_java.models;

public class Album {
    private long id;
    private String name;
    private int photos;

    public Album(long id, String name, int photos) {
        this.id = id;
        this.name = name;
        this.photos = photos;
    }

    public Album(String name, int photos) {
        this.name = name;
        this.photos = photos;
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

    public int getPhotos() {
        return photos;
    }

    public void setPhotos(int photos) {
        this.photos = photos;
    }
}
