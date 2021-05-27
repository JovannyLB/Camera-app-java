package com.gjjg.camera_app_java;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import com.gjjg.camera_app_java.models.Album;
import com.gjjg.camera_app_java.models.DataModel;
import com.gjjg.camera_app_java.models.Util;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    private RecyclerView imagesView;
    private ArrayList<String> imageList = new ArrayList<>();
    private int gridSize = 3;

    private SeekBar gridSeekBar;

    private static final int ACCESS_CAMERA = 1000, SAVE_PHOTO = 1100;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        getSupportActionBar().setTitle(DataModel.getInstance().getAlbum().getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ContextCompat.checkSelfPermission(ImagesActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ImagesActivity.this, new String[] {Manifest.permission.CAMERA}, ACCESS_CAMERA);
        }

        if (ContextCompat.checkSelfPermission(ImagesActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ImagesActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_PHOTO);
        }

        imagesView = findViewById(R.id.imagesView);
        getAlbumPhotos();
        ImageViewAdapter imageAdapter = new ImageViewAdapter(this, imageList);
        imagesView.setLayoutManager(new GridLayoutManager(this, gridSize));
        imagesView.setAdapter(imageAdapter);

        gridSeekBar = findViewById(R.id.gridSeekBar);
        gridSeekBar.setMax(2);
        gridSeekBar.setProgress(gridSize - 1);
        gridSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                UpdateGrid(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void UpdateGrid(int newGridSize) {
        gridSize = newGridSize;
        imagesView.setLayoutManager(new GridLayoutManager(this, gridSize));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.images_activity_appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu1:
                CameraButton();
                break;
            case R.id.menu2:
                GalleryButton();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Uri galleryImageUri, cameraImageUri;
    public static final int PICK_IMAGE = 3;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == ACCESS_CAMERA && resultCode == RESULT_OK){
            imageList.add(cameraImageUri.toString());
            ImageViewAdapter imageAdapter = new ImageViewAdapter(this, imageList);
            imagesView.setAdapter(imageAdapter);
            //Adicionar imagem na database
            if(cameraImageUri != null){
                addPhotoAlbum(cameraImageUri);
            }
        } else if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Uri originalUri;
            if (Build.VERSION.SDK_INT < 19) {
                originalUri = data.getData();
            } else {
                originalUri = data.getData();
                final int takeFlags = data.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                try {
                    getContentResolver().takePersistableUriPermission(originalUri, takeFlags);
                }
                catch (SecurityException e){
                    e.printStackTrace();
                }
            }

            galleryImageUri = originalUri;
            imageList.add(galleryImageUri.toString());
            ImageViewAdapter imageAdapter = new ImageViewAdapter(this, imageList);
            imagesView.setAdapter(imageAdapter);
            //Adicionar imagem na database
            if(galleryImageUri != null){
                addPhotoAlbum(galleryImageUri);
            }
        }
    }

    private void CameraButton(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the greyscale APP");
        cameraImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
        startActivityForResult(camera, ACCESS_CAMERA);
    }

    private void GalleryButton(){
        Intent intent;
        if (Build.VERSION.SDK_INT <19){
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
        }

        startActivityForResult(Intent.createChooser(intent, "Select a picture"), PICK_IMAGE);
    }

    private void addPhotoAlbum(Uri newPhotoUri){
        Album newAlbum = new Album(DataModel.getInstance().getAlbum().getId(), DataModel.getInstance().getAlbum().getName(), DataModel.getInstance().getAlbum().getPhotos());
        List<String> photos = Util.convertStringToList(newAlbum.getPhotos());
        ArrayList<String> photosArray= Util.listToArrayList(photos);

        String uriString = newPhotoUri.toString();
        photosArray.add(uriString);
        newAlbum.setPhotos(Util.convertListToString(photosArray));

        DataModel.getInstance().editAlbum(newAlbum);
    }

    private void getAlbumPhotos(){
        Album newAlbum = DataModel.getInstance().getAlbum();
        ArrayList<String> photos =  Util.listToArrayList(Util.convertStringToList(newAlbum.getPhotos()));
        imageList = photos;
    }
}