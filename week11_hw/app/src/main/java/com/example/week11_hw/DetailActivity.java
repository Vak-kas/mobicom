package com.example.week11_hw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        ImageView imageView = findViewById(R.id.detailImageView);
        TextView titleView = findViewById(R.id.detailNameView);
        TextView descriptionView = findViewById(R.id.detailDescriptionView);

        Intent intent = getIntent();
        int imageId = intent.getIntExtra("imageId", 0);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");

        imageView.setImageResource(imageId);
        titleView.setText(title);
        descriptionView.setText(description);
    }
}