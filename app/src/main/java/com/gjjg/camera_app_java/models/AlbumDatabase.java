package com.gjjg.camera_app_java.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class AlbumDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "albums.sqlite";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Album";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PHOTOS = "photos";

    private Context context;

    public AlbumDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query;/* = "CREATE TABLE IF NOT EXISTS "+DB_TABLE+"("+
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_NAME + " TEXT, "+
                COL_PHOTOS + " INTEGER);";*/
        query = String.format(
                "CREATE TABLE IF NOT EXISTS %s(" +
                " %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " %s TEXT, " +
                " %s TEXT)", DB_TABLE, COL_ID, COL_NAME, COL_PHOTOS);

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //CRUD
    //CREATE - CRIA UMA CITY NO BD
    public long createAlbumInDB(Album album) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        ///storage/emulated/0/Android/data/com.gjjg.camera_app_java/files/Pictures/JPEG_20210522_131720_3898662663182813283.jpg
        ///storage/emulated/0/Android/data/com.gjjg.camera_app_java/files/Pictures/JPEG_20210522_131807_9025589401973945931.jpg
        ArrayList<String> photosList = new ArrayList<String>();

//        photosList.add("/storage/emulated/0/Android/data/com.gjjg.camera_app_java/files/Pictures/JPEG_20210522_131720_3898662663182813283.jpg");
//        photosList.add("/storage/emulated/0/Android/data/com.gjjg.camera_app_java/files/Pictures/JPEG_20210522_131807_9025589401973945931.jpg");

        values.put(COL_NAME, album.getName());
        values.put(COL_PHOTOS, Util.convertListToString(photosList));
//        values.put(COL_PHOTOS, Util.convertListToString(album.getPhotosPaths()));
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return id;
    }

    //RETRIEVE - TRAZER OS DADOS DO BD
    public ArrayList<Album> retrieveAlbumsFromDB() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(DB_TABLE, null, null,
                null, null, null, null);
        ArrayList<Album> albums = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                String photos = cursor.getString(cursor.getColumnIndex(COL_PHOTOS));
                Log.i("AlbumDatabase", "" + photos);

                Album c = new Album(id, name, photos);
                albums.add(c);

            } while (cursor.moveToNext());
        }
        database.close();
        return albums;
    }

    public void deleteAlbumInDB(Album album) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DB_TABLE, "id = ?", new String[]{String.valueOf(album.getId())});
        database.close();
    }

    public void editAlbumInDB(Album album) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, album.getName());
        values.put(COL_PHOTOS, album.getPhotos());
        database.update(DB_TABLE, values, "ID =?", new String[]{String.valueOf(album.getId())});
        database.close();
    }
}
