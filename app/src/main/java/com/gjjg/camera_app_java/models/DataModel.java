package com.gjjg.camera_app_java.models;

import android.content.Context;
import android.util.Log;
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
    private Context context;

    public void setContext(Context context){
        this.context = context;
        database = new AlbumDatabase(context);
        Log.i("DataModel", "teste");
        albums = database.retrieveAlbumsFromDB();
    }

    public ArrayList<Album> getAlbums(){
        return albums;
    }

//    public Album getAlbum(long id){
    public Album getAlbum(){
        Album album = new Album("", new ArrayList<Image>());

        for(Album albumItem : albums){
            if(albumItem.getId() == currentAlbumId){
                album = albumItem;
            }
        }
//        Album album = new Album("", new ArrayList<Image>());
//
//        for(Album albumItem : albums){
//            if(albumItem.getId() == id){
//                album = albumItem;
//            }
//        }

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

    public void editAlbum(Album album, int index){
        database.editAlbumInDB(album);
        albums.set(index, album);
    }

    public void deleteAlbum(Album album, int index){
        database.deleteAlbumInDB(album);
        albums.remove(index);
    }

//    public void removePhoto(int albumIndex, int imageIndex){
//        albums.get(albumIndex).removePhoto(imageIndex);
//        database.editAlbumInDB(albums.get(albumIndex));
//    }

    public int getCurrentAlbumIndex(){
        int index = 0;
        for (int i = 0; i < albums.size(); i++) {
            if(albums.get(i).getId() == currentAlbumId){
                index = i;
                break;
            }
        }
        return index;
    }

    public long getCurrentAlbumId(){
        return currentAlbumId;
    }

    public void setCurrentAlbumId(long currentAlbumId){
        this.currentAlbumId = currentAlbumId;
    }
}
