package com.gjjg.camera_app_java;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.gjjg.camera_app_java.models.Album;
import com.gjjg.camera_app_java.models.DataModel;
import com.gjjg.camera_app_java.models.Util;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ViewHolder> {

    private Context context;
//    private List<Image> data;
//    private long albumId;

    public ImageViewAdapter(Context context/*, long albumId, List<Image> data*/) {
        this.context = context;
//        this.albumId = albumId;
//        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_image, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = DataModel.getInstance().getAlbum();
        Log.w("GETNAME", album.getPhotos().get(position).getName());
        Log.w("GETPATH", album.getPhotos().get(position).getPath());
        holder.imageName.setText(album.getPhotos().get(position).getName());
        holder.imageImage.setImageBitmap(Util.StringToBitMap(album.getPhotos().get(position).getPath()));
//        holder.imageName.setText(album.getPhotos().get(position).getName());
//        holder.imageImage.setImageURI(Uri.parse(album.getPhotos().get(position).getPath()));
        holder.imageImage.setOnLongClickListener((View v) ->
                onLongClick(position, holder)
        );
    }

    public void removeItem(int position) {
        Album currentAlbum = DataModel.getInstance().getAlbum();
        currentAlbum.removePhoto(position);
        DataModel.getInstance().editAlbum(currentAlbum, (int) DataModel.getInstance().getCurrentAlbumIndex());
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
        textView.setText(DataModel.getInstance().getAlbum().getName());

        final FrameLayout layout = new FrameLayout(context);
        layout.setPaddingRelative(80, 45, 45, 0);
        layout.addView(textView);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete Image " + position + "from Album");
        alert.setView(layout);
        alert.setPositiveButton("yes", ((DialogInterface dialog, int which) -> removeItem(position)
        ));
        alert.setNegativeButton("no", ((DialogInterface dialog, int which) -> {
        }));
        alert.show();
    }

    @Override
    public int getItemCount() {
        return DataModel.getInstance().getAlbum().getPhotos().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView imageName;
        ImageView imageImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.textImageName);
            imageImage = itemView.findViewById(R.id.imageImageIcon);
        }
    }
}
