package com.example.week12_hw2;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {

    public SongAdapter(Context context, List<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }

        ImageView songImage = convertView.findViewById(R.id.songImage);
        TextView songTitle = convertView.findViewById(R.id.songTitle);
        TextView songDuration = convertView.findViewById(R.id.songTime);
        TextView singer = convertView.findViewById(R.id.singer);

        songImage.setImageResource(song.getImageResId());
        songTitle.setText(song.getTitle());
        singer.setText(song.getSinger());

        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), song.getSongResId());
        int duration = mediaPlayer.getDuration();
        mediaPlayer.release();

        songDuration.setText(formatTime(duration));

        return convertView;
    }

    private String formatTime(int milliseconds) {
        int minutes = milliseconds / 1000 / 60;
        int seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
