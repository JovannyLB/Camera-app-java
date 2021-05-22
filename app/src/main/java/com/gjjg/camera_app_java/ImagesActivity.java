package com.gjjg.camera_app_java;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.Toast;

import com.gjjg.camera_app_java.models.Album;
import com.gjjg.camera_app_java.models.DataModel;
import com.gjjg.camera_app_java.models.Image;
import com.gjjg.camera_app_java.models.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImagesActivity extends AppCompatActivity {

    private List<Image> testImageList;

    private RecyclerView imagesView;
    private ImageViewAdapter imageAdapter;
    private int gridSize = 3;

    private SeekBar gridSeekBar;

//    private long albumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

//        albumId = getIntent().getLongExtra("ALBUM_ID", 1);

//        Log.i("albumID", "" + albumId);
//        if(albumId == -1){
//            Log.e("album","Erro ao abrir album");
//            return;
//        }

        getSupportActionBar().setTitle("Album X");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testImageList = new ArrayList<>();
//        testImageList.add(new Image("Poly monkey", R.drawable.monkey1));
//        testImageList.add(new Image("Cool monkey", R.drawable.monkey2));
//        testImageList.add(new Image("Yoda Gaming", R.drawable.yoda));
//        testImageList.add(new Image("Cube frog", R.drawable.frog));
//        testImageList.add(new Image("Square Jerry", R.drawable.jerry));

        imagesView = findViewById(R.id.imagesView);
        imageAdapter = new ImageViewAdapter(this);

        gridSeekBar = findViewById(R.id.gridSeekBar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.images_activity_appbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu1:
                dispatchTakePictureIntent();
                break;
            case R.id.menu2:
                dispatchOpenGalleryIntent();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

//            Log.i("image", "INFERNO");
//            String path = data.getStringExtra("path");

//            Log.i("image", "INFERNO");
//            String path = currentPhotoPath;
//            Log.i("image", "" + path);
//            addNewPhoto(/*path*/);

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String bitmapString = Util.BitMapToString(imageBitmap);
            Log.i("image", bitmapString);
            addNewPhoto(bitmapString);

//            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == PICK_IMAGE){
            try {
                final Uri imageUri = data.getData();
                Log.i("image", imageUri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                String bitmapString = Util.BitMapToString(bitmap);
                addNewPhoto(bitmapString);
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                image_view.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
//            Log.i("teste", "veio");
////            Bundle extras = data.getExtras();
//            Log.i("extra", data.getExtras().toString());
////            Bitmap imageBitmap = (Bitmap) extras.get("data");
////            Log.i("image", imageBitmap.toString());
//        }

//        Bitmap bitmap = data.getExtras().get("imageKey");
//        imageView.setBitmapImage(bitmap);
    }

    public static final int PICK_IMAGE = 3;
    private void dispatchOpenGalleryIntent() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    static final int REQUEST_IMAGE_CAPTURE = 2;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("photo intent", ex.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                Log.i("tostring", photoURI.toString());
                Log.i("getpath", photoURI.getPath());
//                takePictureIntent.putExtra("path", (String) photoURI.getPath());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.getPath());
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    String currentPhotoPath;
    Uri currentPhotoUri;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoUri = Uri.fromFile(image);
        currentPhotoPath = image.getAbsolutePath();
        Log.i("path", currentPhotoPath);
        return image;
    }

    private void addNewPhoto(/*String path*/String bitmapString) {
        Log.i("edit", "ADD PHOTO " + currentPhotoUri.getEncodedPath());

        Album currentAlbum = DataModel.getInstance().getAlbum();
        currentAlbum.addPhoto(new Image("Nome", bitmapString));
        DataModel.getInstance().editAlbum(currentAlbum, (int) DataModel.getInstance().getCurrentAlbumIndex());

//        Album currentAlbum = DataModel.getInstance().getAlbum();
//        currentAlbum.addPhoto(new Image("Nome", currentPhotoUri.getEncodedPath()));
//        DataModel.getInstance().editAlbum(currentAlbum, (int) DataModel.getInstance().getCurrentAlbumIndex());

//        DataModel.getInstance().getAlbum().addPhoto(new Image("Nome", path));
//        finish();
//        startActivity(getIntent());
    }
}