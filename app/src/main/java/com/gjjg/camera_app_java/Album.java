package com.gjjg.camera_app_java;

public class Album {

    private String name;
    private int image;

    public Album(String name) {
        this.name = name;
        this.image = R.drawable.folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
