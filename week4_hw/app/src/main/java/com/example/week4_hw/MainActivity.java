package com.example.week4_hw;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    CheckBox girl, boy;
    ImageView image;
    RadioGroup member, screen;
    RadioButton member1, member2, member3;
    RadioButton fitCenter, Mat, fitXY, center;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.idol);

        girl = (CheckBox) findViewById(R.id.girlGroup);
        boy = (CheckBox) findViewById(R.id.boyGroup);
        member1 = findViewById(R.id.member1);
        member2 = findViewById(R.id.member2);
        member3 = findViewById(R.id.member3);
        image = (ImageView) findViewById(R.id.picture);
        image.setImageResource(R.drawable.ses_bada);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);







        member=findViewById(R.id.member);
        member.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.member1 && member1.getText().toString().equals("바다")) {
                    image.setImageResource(R.drawable.ses_bada);
                }
                else if(checkedId == R.id.member1 && member1.getText().toString().equals("김종국")){
                    image.setImageResource(R.drawable.turbo_jongkuk);
                }
                else if (checkedId == R.id.member2 && member2.getText().toString().equals("유진")) {
                    image.setImageResource(R.drawable.ses_yujin);
                }
                else if(checkedId == R.id.member2 && member2.getText().toString().equals("김정남")){
                    image.setImageResource(R.drawable.turbo_jeongnam);
                }
                else if (checkedId == R.id.member3 && member3.getText().toString().equals("슈")) {
                    image.setImageResource(R.drawable.ses_shu);
                }
                else if(checkedId == R.id.member3 && member3.getText().toString().equals("마이키")){
                    image.setImageResource(R.drawable.turbo_mike);
                }
            }
        });




        boy.setOnCheckedChangeListener(mCheckListener);
        girl.setOnCheckedChangeListener(mCheckListener);



        //RadioButton fitCenter, Matrix, fitXY, center;
        fitCenter = findViewById(R.id.fitCenter);
        Mat = findViewById(R.id.Matrix);
        fitXY = findViewById(R.id.fitXY);
        center = findViewById(R.id.center);
        RadioGroup positionGroup = (RadioGroup) findViewById(R.id.position);
        positionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.fitCenter) {
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else if (checkedId == R.id.Matrix) {
                    image.setScaleType(ImageView.ScaleType.MATRIX);
                    Matrix matrix = image.getImageMatrix();
                    float scale = 0.2f;
                    matrix.setScale(scale, scale);
                    image.setImageMatrix(matrix);
                } else if (checkedId == R.id.fitXY) {
                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                } else if (checkedId == R.id.center){
                    image.setScaleType(ImageView.ScaleType.CENTER);
                }
            }
        });



    }

    CompoundButton.OnCheckedChangeListener mCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            StringBuilder mStr = new StringBuilder();

            if(buttonView.getId() == R.id.boyGroup){
                if(isChecked){
                    girl.setChecked(false);
                    member1.setText("김종국");
                    member2.setText("김정남");
                    member3.setText("마이키");
                    member.check(R.id.member1);
                    image.setImageResource(R.drawable.turbo_jongkuk);
                    fitCenter.setChecked(true);
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);

                }
                else{
                    girl.setChecked(true);
                    member1.setText("바다");
                    member2.setText("유진");
                    member3.setText("슈");
                    member.check(R.id.member1);
                    image.setImageResource(R.drawable.ses_bada);
                    fitCenter.setChecked(true);
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }

            }

            else{
                if(isChecked){
                    boy.setChecked(false);
                    member1.setText("바다");
                    member2.setText("유진");
                    member3.setText("슈");
                    member.check(R.id.member1);
                    image.setImageResource(R.drawable.ses_bada);
                    fitCenter.setChecked(true);
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                else{
                    boy.setChecked(true);
                    member1.setText("김종국");
                    member2.setText("김정남");
                    member3.setText("마이키");
                    member.check(R.id.member1);
                    image.setImageResource(R.drawable.turbo_jongkuk);
                    fitCenter.setChecked(true);
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }


        }

    };


}
