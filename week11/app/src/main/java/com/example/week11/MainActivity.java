package com.example.week11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }


    public void simpleClick(View v){
        Intent intent = new Intent(MainActivity.this, simple_activity.class);
        startActivity(intent);

    }

    public void addeleteclick(View v){
        Intent intent2 = new Intent(MainActivity.this, simple_add_delete_activity.class);
        startActivity(intent2);
    }

    public void customClick(View v){
        Intent intent3 = new Intent(MainActivity.this, customActivity.class);
        startActivity(intent3);
    }
}