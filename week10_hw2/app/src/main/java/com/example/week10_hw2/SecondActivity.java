package com.example.week10_hw2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private int[] counts;
    private RatingBar[] ratingBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        counts = getIntent().getIntArrayExtra("counts");

        ratingBars = new RatingBar[3];
        ratingBars[0] = findViewById(R.id.ratingBar1);
        ratingBars[1] = findViewById(R.id.ratingBar2);
        ratingBars[2] = findViewById(R.id.ratingBar3);

        for (int i = 0; i < counts.length; i++) {
            ratingBars[i].setRating(Math.min(counts[i], 5)); // 최대 5개까지 표시
        }

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < counts.length; i++) {
                    counts[i] = (int) ratingBars[i].getRating();
                }
                Intent resultIntent = new Intent(SecondActivity.this, MainActivity.class);
                resultIntent.putExtra("counts", counts);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(resultIntent);
                finish(); // 현재 액티비티를 종료하여 MainActivity로 돌아갑니다.
            }
        });
    }
}