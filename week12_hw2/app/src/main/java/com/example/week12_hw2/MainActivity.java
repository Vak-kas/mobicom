package com.example.week12_hw2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView songListView;
    TextView titleTextView;
    TextView nowTimeTextView;
    TextView totalTimeTextView;
    SeekBar seekBar;
    Button previousButton;
    Button playPauseButton;
    Button nextButton;
    ImageView songImageView;

    MediaPlayer mediaPlayer;
    List<Song> songList;
    int currentSongIndex = 0;
    boolean isPlaying = false;
    Handler handler = new Handler();
    Runnable updateSeekBarRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songListView = findViewById(R.id.songListView);
        titleTextView = findViewById(R.id.title);
        nowTimeTextView = findViewById(R.id.nowTime);
        totalTimeTextView = findViewById(R.id.totalTime);
        seekBar = findViewById(R.id.seek);
        previousButton = findViewById(R.id.previous);
        playPauseButton = findViewById(R.id.playPause);
        nextButton = findViewById(R.id.next);
        songImageView = findViewById(R.id.image);

        // Sample song list
        songList = new ArrayList<>();
        songList.add(new Song("아센디오", R.drawable.ive, R.raw.ive, "아이브"));
        songList.add(new Song("이브 프시케 그리고 푸른 수염", R.drawable.less, R.raw.les, "르세라핌"));
        songList.add(new Song("핫스윗", R.drawable.newjeans, R.raw.newjeans, "뉴진스"));

        SongAdapter adapter = new SongAdapter(this, songList);
        songListView.setAdapter(adapter);
        songListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSongIndex = position;
                playSong();
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseSong();
                } else {
                    resumeSong();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSong();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Initialize the update seek bar runnable
        updateSeekBarRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && isPlaying) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    nowTimeTextView.setText(formatTime(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    private void updateSelection(int index) {
        songListView.setItemChecked(index, true);
        songListView.setSelection(index);
    }

    private void playSong() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        int songResId = songList.get(currentSongIndex).getSongResId();
        mediaPlayer = MediaPlayer.create(this, songResId);
        mediaPlayer.start();
        isPlaying = true;
        titleTextView.setText(songList.get(currentSongIndex).getTitle());
        songImageView.setImageResource(songList.get(currentSongIndex).getImageResId());

        mediaPlayer.setOnCompletionListener(mp -> nextSong());

        seekBar.setMax(mediaPlayer.getDuration());
        totalTimeTextView.setText(formatTime(mediaPlayer.getDuration()));

        handler.post(updateSeekBarRunnable);
        updateSelection(currentSongIndex); // 현재 선택 항목 업데이트
    }

    private void pauseSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            handler.removeCallbacks(updateSeekBarRunnable);
        }
    }

    private void resumeSong() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            handler.post(updateSeekBarRunnable);
        }
    }

    private void previousSong() {
        currentSongIndex = (currentSongIndex - 1 + songList.size()) % songList.size();
        playSong();
    }

    private void nextSong() {
        currentSongIndex = (currentSongIndex + 1) % songList.size();
        playSong();
    }

    private String formatTime(int milliseconds) {
        int minutes = milliseconds / 1000 / 60;
        int seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            handler.removeCallbacks(updateSeekBarRunnable);
        }
        super.onDestroy();
    }
}
