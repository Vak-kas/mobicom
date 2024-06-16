package com.example.lastproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private List<Folder> folderList;
    private OnFolderClickListener listener;

    public FolderAdapter(List<Folder> folderList, OnFolderClickListener listener) {
        this.folderList = folderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        Folder folder = folderList.get(position);
        holder.textView.setText(folder.getName());
        holder.itemView.setOnClickListener(v -> listener.onFolderClick(folder));
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.folder_name);
        }
    }

    public interface OnFolderClickListener {
        void onFolderClick(Folder folder);
    }
}


