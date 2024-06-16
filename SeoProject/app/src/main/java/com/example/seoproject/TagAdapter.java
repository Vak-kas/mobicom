package com.example.seoproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private List<String> tagList;
    private OnTagClickListener listener;

    public TagAdapter(List<String> tagList, OnTagClickListener listener) {
        this.tagList = tagList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_item, parent, false);
        return new TagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        String tag = tagList.get(position);
        holder.tagName.setText(tag);

        // 태그 선택 상태에 따라 배경색 변경
        if (listener.getSelectedTags().contains(tag)) {
            holder.itemView.setBackgroundResource(R.drawable.selected_tag_background);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.default_tag_background);
        }

        holder.itemView.setOnClickListener(v -> listener.onTagClick(tag));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tag_name);
        }
    }

    public interface OnTagClickListener {
        void onTagClick(String tag);
        Set<String> getSelectedTags();
    }
}
