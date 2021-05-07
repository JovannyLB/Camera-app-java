package com.gjjg.camera_app_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private List<Album> testAlbumList;

    private RecyclerView albumsView;
    private AlbumViewAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        getSupportActionBar().setTitle("Album List");

        testAlbumList = new ArrayList<>();
        testAlbumList.add(new Album("Album Teste 1"));
        testAlbumList.add(new Album("Album Teste 2"));
        testAlbumList.add(new Album("Album Teste 3"));
        testAlbumList.add(new Album("Album Teste 4"));
        testAlbumList.add(new Album("Album Teste 5"));

        albumsView = (RecyclerView) findViewById(R.id.albumsView);
        albumAdapter = new AlbumViewAdapter(this, testAlbumList);

        albumsView.setAdapter(albumAdapter);
        albumsView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}