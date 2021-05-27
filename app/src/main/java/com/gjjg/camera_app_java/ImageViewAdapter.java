package com.gjjg.camera_app_java;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.gjjg.camera_app_java.models.Album;
import com.gjjg.camera_app_java.models.DataModel;
import com.gjjg.camera_app_java.models.Util;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> data;

    public ImageViewAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
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
        if (data.size() > 0) {
            holder.imageImage.setImageURI(Uri.parse(data.get(position)));
            holder.imageName.setText("" + position);
            holder.imageImage.setOnLongClickListener((View v) ->
                    onLongClick(position, holder)
            );
        }
    }

    private void removePhotoAlbum(int position) {
        Album newAlbum = new Album(DataModel.getInstance().getAlbum().getId(), DataModel.getInstance().getAlbum().getName(), DataModel.getInstance().getAlbum().getPhotos());
        List<String> photos = Util.convertStringToList(newAlbum.getPhotos());
        ArrayList<String> photosArray = Util.listToArrayList(photos);

        photosArray.remove(position);
        newAlbum.setPhotos(Util.convertListToString(photosArray));

        DataModel.getInstance().editAlbum(newAlbum);
    }

    public void removeItemFromAdapter(int position) {
        data.remove(position);
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
        alert.setTitle("Delete Image " + (position + 1) + " from Album");
        alert.setView(layout);
        alert.setPositiveButton("yes", ((DialogInterface dialog, int which) -> {
            removePhotoAlbum(position);
            removeItemFromAdapter(position);
        }
        ));
        alert.setNegativeButton("no", ((DialogInterface dialog, int which) -> {
        }));
        alert.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
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
