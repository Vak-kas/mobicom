package com.example.week4;

import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    CheckBox check1, check2;
    RadioGroup rGroup1;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        check1 = (CheckBox) findViewById(R.id.checkBox1);
        check2 = (CheckBox) findViewById(R.id.checkBox2);

        imageView1 = (ImageView) findViewById(R.id.imageView);
//        imageView1.setScaleType(ImageView.ScaleType.FIT_START);
        imageView1.setImageResource(R.drawable.an);

        imageView1.setScaleType(ImageView.ScaleType.MATRIX);
        Matrix matrix = imageView1.getImageMatrix();
        float scale = 0.2f;
        matrix.setScale(scale, scale);
        imageView1.setImageMatrix(matrix);



        check1.setOnCheckedChangeListener(mCheckListener);
        check2.setOnCheckedChangeListener(mCheckListener);

        rGroup1 = (RadioGroup) findViewById(R.id.rgroup1);
        rGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) { // 메서드 이름 수정

                StringBuilder mStr = new StringBuilder();

                if(checkedId == R.id.man) {
                    mStr.append("MAN Selected");
                } else {
                    mStr.append("WOMAN Selected");
                }

                Toast.makeText(MainActivity.this, mStr.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    CompoundButton.OnCheckedChangeListener mCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            StringBuilder mStr = new StringBuilder();

            if (buttonView.getId() == R.id.checkBox1)
                mStr.append(check1.getText().toString());
            else
                mStr.append(check2.getText().toString());

            if (isChecked)
                mStr.append(" 선택됨");
            else
                mStr.append(" 선택 해제됨");

            Toast.makeText(MainActivity.this, mStr.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}