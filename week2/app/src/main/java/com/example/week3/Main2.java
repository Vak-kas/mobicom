package com.example.week3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.*;

public class Main2 extends AppCompatActivity {

    EditText screen;
    Button calculate, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc2);

        screen = findViewById(R.id.screen);
        calculate = findViewById(R.id.cal);

        // 버튼에 클릭 리스너 추가
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView에서 텍스트 가져오기
                String expression = screen.getText().toString();

                // mxparser를 사용하여 계산하기
                Expression e = new Expression(expression);
                double result = e.calculate();

                // 결과값 출력하기
                expression += "=";
                expression += Double.toString(result);
                screen.setText(expression);
            }
        });

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                screen.setText("");

            }

        });
    }
}
