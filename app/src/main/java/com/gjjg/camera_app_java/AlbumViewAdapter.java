package com.gjjg.camera_app_java;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gjjg.camera_app_java.models.Album;
import com.gjjg.camera_app_java.models.DataModel;

import java.util.List;

public class AlbumViewAdapter extends RecyclerView.Adapter<AlbumViewAdapter.ViewHolder> {

    private Context context;
//    private List<Album> data;

    public AlbumViewAdapter(Context context/*, List<Album> data*/) {
        this.context = context;
//        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_album, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = DataModel.getInstance().getAlbums().get(position);
        holder.albumName.setText(album.getName());
        holder.albumImage.setImageResource(album.getImage());
        holder.cardView.setOnLongClickListener((View v) ->
                onLongClick(position, holder)
        );
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImagesActivity.class);
//                Log.e("errorororo", "" + album.getId());
//                intent.putExtra("ALBUM_ID", album.getId());
                DataModel.getInstance().setCurrentAlbumId(album.getId());
                context.startActivity(intent);
            }
        });
    }

    public void removeItem(int position) {
        DataModel.getInstance().deleteAlbum(DataModel.getInstance().getAlbums().get(position), position);
//        data.remove(position);
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean onLongClick(int position, ViewHolder holder) {
        showDeleteAlbumDialog(position);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showDeleteAlbumDialog(int position) {
        final TextView textView = new TextView(context);
        textView.setText(DataModel.getInstance().getAlbums().get(position).getName());

        final FrameLayout layout = new FrameLayout(context);
        layout.setPaddingRelative(80, 45, 45, 0);
        layout.addView(textView);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete Album");
        alert.setView(layout);
        alert.setPositiveButton("yes", ((DialogInterface dialog, int which) -> removeItem(position)
        ));
        alert.setNegativeButton("no", ((DialogInterface dialog, int which) -> {
        }));
        alert.show();
    }

    @Override
    public int getItemCount() {
        return DataModel.getInstance().getAlbums().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView albumName;
        ImageView albumImage;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.textAlbumName);
            albumImage = itemView.findViewById(R.id.imageAlbumIcon);
            cardView = (CardView) itemView.findViewById(R.id.albumCardView);
        }
    }
}
