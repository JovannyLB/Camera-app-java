package com.gjjg.camera_app_java;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gjjg.camera_app_java.models.Album;
import com.gjjg.camera_app_java.models.DataModel;

public class AlbumActivity extends AppCompatActivity {

    private RecyclerView albumsView;
    private AlbumViewAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        getSupportActionBar().setTitle("Album List");


        DataModel.getInstance().setContext(this);

        albumsView = findViewById(R.id.albumsView);
        albumAdapter = new AlbumViewAdapter(this);

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

        alert.setPositiveButton("create", ((DialogInterface dialog, int which) -> {
            if(edittext.getText().length() > 0){
                createNewAlbum(edittext.getText().toString());
            } else {
                Toast.makeText(this,"Insert a valid album name", Toast.LENGTH_LONG).show();
            }
        }
        ));
        alert.setNegativeButton("back", ((DialogInterface dialog, int which) -> {
        }));
        alert.show();
    }

    private void createNewAlbum(String name) {
        DataModel.getInstance().addAlbum(new Album(name, ""));
    }
}