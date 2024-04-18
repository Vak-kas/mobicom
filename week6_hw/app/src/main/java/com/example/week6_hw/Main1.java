package com.example.week6_hw;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Main1 extends AppCompatActivity {

    DatePicker datePicker;
    SeekBar seekBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.hw1);


        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);

        datePicker = findViewById(R.id.datePicker);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 1월, 3월, 5월, 7월, 8월 10월, 12월 이면 Max 를 31일까지 설정
                if (monthOfYear == 0 || monthOfYear == 2 || monthOfYear == 4 || monthOfYear == 6 || monthOfYear == 7 || monthOfYear == 9
                    ||monthOfYear == 11){
                    seekBar.setMax(31);
                }
                //2월이라면
                else if (monthOfYear == 1){
                    //윤년의 조건
                    //1. 4로 나누어 떨어지면서 100으로는 나누어 떨어지지 않을 때
                    if(year%4 == 0 && year %100 !=0){
                        seekBar.setMax(29);
                    } // 2. 100으로 나누어 떨어지나 400으로는 나누어 떨어지면 윤년
                    else if(year%100 == 0 && year%400 ==0) {
                        seekBar.setMax(29);
                    }
                    // 그 외 나머지는 윤년이 아님
                    else {
                        seekBar.setMax(28);
                    }

                }
                // 그 외 나머지 달들은 30일까지 설정
                else{
                    seekBar.setMax(30);
                }

                //SeekBar 업데이트
                seekBar.setProgress(dayOfMonth);

                //텍스트 뷰 업데이트
                String monthDisplay = (monthOfYear + 1) + "월 (" + dayOfMonth + "/" + seekBar.getMax() + ")";
                textView.setText(monthDisplay);

            }
        });

    }
}