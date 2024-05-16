package com.example.week10_hw2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int[] imageIdx = {R.id.image1, R.id.image2, R.id.image3};
    private int[] textIdx = {R.id.text1, R.id.text2, R.id.text3};
    private int[] counts = {0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        int[] updatedCounts = intent.getIntArrayExtra("counts");
        if (updatedCounts != null) {
            counts = updatedCounts;
            for (int i = 0; i < textIdx.length; i++) {
                TextView textView = findViewById(textIdx[i]);
                textView.setText(String.valueOf(counts[i]));
            }
        }

        for (int i = 0; i < imageIdx.length; i++) {
            final int index = i;
            ImageView imageView = findViewById(imageIdx[i]);
            final TextView textView = findViewById(textIdx[i]);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counts[index]++;
                    textView.setText(String.valueOf(counts[index]));
                }
            });
        }

        Button resultButton = findViewById(R.id.result);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("counts", counts);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}