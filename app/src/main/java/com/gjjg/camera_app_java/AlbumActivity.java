package com.gjjg.camera_app_java;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.gjjg.camera_app_java.models.Album;
import com.gjjg.camera_app_java.models.DataModel;
import com.gjjg.camera_app_java.models.Image;
import com.gjjg.camera_app_java.models.Util;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private ArrayList<Album> testAlbumList;

    private RecyclerView albumsView;
    private AlbumViewAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        getSupportActionBar().setTitle("Album List");


        DataModel.getInstance().setContext(this);
//        testAlbumList = DataModel.getInstance().getAlbums();

//        List<String> tste = new String[8];

//        testAlbumList.add(new Album("Album Teste 1", ));
//        testAlbumList.add(new Album("Album Teste 2"));
//        testAlbumList.add(new Album("Album Teste 3"));
//        testAlbumList.add(new Album("Album Teste 4"));
//        testAlbumList.add(new Album("Album Teste 5"));

        albumsView = findViewById(R.id.albumsView);
        albumAdapter = new AlbumViewAdapter(this/*, testAlbumList*/);

        albumsView.setAdapter(albumAdapter);
        albumsView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.album_activity_appbar, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showCreateAlbumDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showCreateAlbumDialog() {
        final EditText edittext = new EditText(this);
        edittext.setHint("Enter Name");
        edittext.setMaxLines(1);

        final FrameLayout layout = new FrameLayout(this);
        layout.setPaddingRelative(45, 15, 45, 0);
        layout.addView(edittext);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Create Album");
        alert.setView(layout);
        alert.setPositiveButton("create", ((DialogInterface dialog, int which) -> createNewAlbum(edittext.getText().toString())
        ));
        alert.setNegativeButton("back", ((DialogInterface dialog, int which) -> {
        }));
        alert.show();
    }

    private void createNewAlbum(String name) {
//        Log.i("create", "CREATE ALBUM " + name);
        DataModel.getInstance().addAlbum(new Album(name, new ArrayList<Image>(0)));
//        finish();
//        startActivity(getIntent());
    }
}