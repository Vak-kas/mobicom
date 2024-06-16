package com.example.seoproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private final List<String> folderList;
    private final OnFolderClickListener onFolderClickListener;
    private boolean showRadioButton = false;
    private int selectedPosition = -1;

    public FolderAdapter(List<String> folderList, OnFolderClickListener onFolderClickListener) {
        this.folderList = folderList;
        this.onFolderClickListener = onFolderClickListener;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        String folderName = folderList.get(position);
        holder.folderName.setText(folderName);
        holder.radioButton.setChecked(position == selectedPosition);
        holder.radioButton.setVisibility(showRadioButton ? View.VISIBLE : View.GONE);

        holder.folderImage.setImageResource(R.drawable.ic_folder_placeholder); // Example placeholder

        holder.itemView.setOnClickListener(v -> {
            if (showRadioButton) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
            onFolderClickListener.onFolderClick(folderName);
        });

        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public void setShowRadioButton(boolean show) {
        this.showRadioButton = show;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    public String getSelectedFolder() {
        if (selectedPosition >= 0 && selectedPosition < folderList.size()) {
            return folderList.get(selectedPosition);
        }
        return null;
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {

        TextView folderName;
        ImageView folderImage;
        RadioButton radioButton;

        FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.folder_name);
            folderImage = itemView.findViewById(R.id.folder_image);
            radioButton = itemView.findViewById(R.id.folder_radio_button);
        }
    }

    interface OnFolderClickListener {
        void onFolderClick(String folderName);
    }
}
