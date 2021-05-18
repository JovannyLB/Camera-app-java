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
    private Context context;

    public void setContext(Context context){
        this.context = context;
        database = new AlbumDatabase(context);
        albums = database.retrieveAlbumsFromDB();
    }

    public ArrayList<Album> getAlbums(){
        return albums;
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

    public void editAlbum(Album album, int index){
        database.editAlbumInDB(album);
        albums.set(index, album);
    }

    public void deleteAlbum(Album album, int index){
        database.deleteAlbumInDB(album);
        albums.remove(index);
    }
}
