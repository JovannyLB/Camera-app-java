package com.gjjg.camera_app_java.models;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class DataModel {
    private static DataModel instance = new DataModel();
    private DataModel(){

    }
    public static DataModel getInstance(){
        return instance;
    }
    private AlbumDatabase database;
    private ArrayList<Album> albums;
    private long currentAlbumId;
    private int currentAlbumIndex;
    private Context context;

    public void setContext(Context context){
        this.context = context;
        database = new AlbumDatabase(context);
        albums = database.retrieveAlbumsFromDB();
    }

    public ArrayList<Album> getAlbums(){
        return albums;
    }

    public Album getAlbum(){
        Album album = new Album("", "");

        for(Album albumItem : albums){
            if(albumItem.getId() == currentAlbumId){
                album = albumItem;
            }
        }

        return album;
    }

    public void addAlbum(Album album){
        long id = database.createAlbumInDB(album);

        if(id > 0){
            album.setId(id);
            albums.add(album);
        }else{
            Toast.makeText(
                    context,"Add album problem",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void editAlbum(Album album){
        database.editAlbumInDB(album);
        albums.set(currentAlbumIndex, album);
    }

    public void deleteAlbum(Album album, int index){
        database.deleteAlbumInDB(album);
        albums.remove(index);
    }

    public long getCurrentAlbumId(){
        return currentAlbumId;
    }

    public void setCurrentAlbumId(long currentAlbumId){
        this.currentAlbumId = currentAlbumId;
    }

    public int getCurrentAlbumIndex(){
        return currentAlbumIndex;
    }

    public void setCurrentAlbumIndex(int currentAlbumIndex){
        this.currentAlbumIndex = currentAlbumIndex;
    }
}
