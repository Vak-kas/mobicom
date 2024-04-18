package com.example.week6_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnnext, btnpre, btnauto;
    ViewFlipper viewFilpper;
    boolean autoflipflag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnnext = (Button) findViewById(R.id.btnnext);
        btnpre = (Button) findViewById(R.id.btnpre);
        btnauto = (Button) findViewById(R.id.btnauto);
        viewFilpper = (ViewFlipper) findViewById(R.id.viewfilpper);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFilpper.showNext();
            }
        });


        btnpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFilpper.showPrevious();
            }
        });

        btnauto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                autoflipflag = !autoflipflag;
                if(autoflipflag){
                    viewFilpper.startFlipping();;
                    viewFilpper.setFlipInterval(1000);
                }
                else{
                    viewFilpper.stopFlipping();
                }
            }
        });
    }
}