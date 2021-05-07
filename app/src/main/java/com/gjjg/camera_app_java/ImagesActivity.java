package com.gjjg.camera_app_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    private List<Image> testImageList;

    private RecyclerView imagesView;
    private ImageViewAdapter imageAdapter;
    private int gridSize = 3;

    private SeekBar gridSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        getSupportActionBar().setTitle("Album X");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testImageList = new ArrayList<>();
        testImageList.add(new Image("Poly monkey", R.drawable.monkey1));
        testImageList.add(new Image("Cool monkey", R.drawable.monkey2));
        testImageList.add(new Image("Yoda Gaming", R.drawable.yoda));
        testImageList.add(new Image("Cube frog", R.drawable.frog));
        testImageList.add(new Image("Square Jerry", R.drawable.jerry));

        imagesView = (RecyclerView) findViewById(R.id.imagesView);
        imageAdapter = new ImageViewAdapter(this, testImageList);

        gridSeekBar = (SeekBar) findViewById(R.id.gridSeekBar);
        gridSeekBar.setMax(2);
        gridSeekBar.setProgress(gridSize - 1);

        gridSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                UpdateGrid(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imagesView.setAdapter(imageAdapter);
        imagesView.setLayoutManager(new GridLayoutManager(this, gridSize));
    }

    private void UpdateGrid(int newGridSize) {
        gridSize = newGridSize;
        imagesView.setLayoutManager(new GridLayoutManager(this, gridSize));
    }
}