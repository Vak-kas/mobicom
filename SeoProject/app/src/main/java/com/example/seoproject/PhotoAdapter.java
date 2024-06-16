package com.example.seoproject;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> photoList;
    private List<Uri> selectedPhotos;
    private OnPhotoActionListener listener;

    public PhotoAdapter(List<Photo> photoList, OnPhotoActionListener listener) {
        this.photoList = photoList;
        this.listener = listener;
        this.selectedPhotos = new ArrayList<>();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        Uri photoUri = Uri.parse(photo.getImageUri());

        holder.photoImageView.setImageURI(photoUri);
        holder.photoTagsTextView.setText(photo.getFormattedTags());

        holder.photoCheckBox.setOnCheckedChangeListener(null);
        holder.photoCheckBox.setChecked(selectedPhotos.contains(photoUri));
        holder.photoCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedPhotos.add(photoUri);
            } else {
                selectedPhotos.remove(photoUri);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            showPopupMenu(v, photo, position);
            return true;
        });
    }

    private void showPopupMenu(View view, Photo photo, int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.photo_popup_menu);

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.edit_tags) {
                listener.onEditTags(photo, position);
                return true;
            } else if (item.getItemId() == R.id.delete_photo) {
                listener.onDeletePhoto(photo, position);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void updatePhotoList(List<Photo> newPhotoList) {
        photoList = newPhotoList;
        notifyDataSetChanged();
    }

    public List<Uri> getSelectedPhotos() {
        return selectedPhotos;
    }

    public void clearSelectedPhotos() {
        selectedPhotos.clear();
        notifyDataSetChanged();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView photoTagsTextView;
        CheckBox photoCheckBox;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photo_view);
            photoTagsTextView = itemView.findViewById(R.id.photo_tags_text_view);
            photoCheckBox = itemView.findViewById(R.id.photo_check_box);
        }
    }

    public interface OnPhotoActionListener {
        void onEditTags(Photo photo, int position);
        void onDeletePhoto(Photo photo, int position);
    }
}
