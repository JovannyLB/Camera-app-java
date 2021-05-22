package com.gjjg.camera_app_java.models;

import android.util.Log;

import com.gjjg.camera_app_java.R;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private long id;
    private String name;
    private List<Image> photos;
    private int image;

//    public Album(long id, String name, List<Image> photos) {
//        this.id = id;
//        this.name = name;
//        this.photos = photos;
//        this.image = R.drawable.folder;
//    }

    public Album(long id, String name, List<String> paths) {
        this.id = id;
        this.name = name;
        this.photos = buildPhotosByPaths(paths);
        this.image = R.drawable.folder;
    }

    public Album(String name, List<Image> photos) {
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

    public List<Image> getPhotos() {
        return photos;
    }

    public List<String> getPhotosPaths() {
        ArrayList<String> paths = new ArrayList<String>();

        for (Image image : photos){
            paths.add(image.getPath());
        }

        return paths;
    }

    public ArrayList<Image> buildPhotosByPaths(List<String> paths) {
        ArrayList<Image> newPhotos = new ArrayList<Image>();

        Log.i("Album.java", name);
        for (String path : paths){
            newPhotos.add(new Image("Nome", path));
            Log.i("Album.java", path);
        }

        return newPhotos;
    }

    public void setPhotos(List<Image> photos) {
        this.photos = photos;
    }

    public int getImage() {
        return image;
    }

    public void removePhoto(int index){
        photos.remove(index);
    }

    public void addPhoto(Image newPhoto){
        photos.add(newPhoto);
    }
}
