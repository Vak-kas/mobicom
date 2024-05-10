package com.example.week10_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    TextView tv;
    int result;
    int ActivityResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.third);

        TextView tv = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();

        if (intent != null){
            result = intent.getIntExtra("num1", 0) + intent.getIntExtra("num2", 0);
            String name = intent.getStringExtra("name");

            tv.setText("Received Name: " + name + "Add value " + result);

        }

    }


    public void backClick(View v){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("resultvalue", result);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void thirdAgain(View v){
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void first(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
