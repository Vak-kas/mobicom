package com.example.week3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.*;

import androidx.activity.EdgeToEdge;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Main1 extends AppCompatActivity implements View.OnClickListener {
    //초기화가 되어 있는가? (기본 상태인가?)
    boolean ac= true;
    boolean finish = false;

    // 소수점 버튼 현재 존재하는가?
    boolean dot = false;
    TextView screen;

    //버튼 변수 설정
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    Button reset, c;

    Button plus, minus, mul, div, ddot;

    Button result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);

        screen = findViewById(R.id.screen);
        ac = true;

        reset = findViewById(R.id.button_reset);
        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ac = true;
                finish = false;
                screen.setText("0");
            }
        });

        c = findViewById(R.id.button_c);
        c.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String current = screen.getText().toString();
                if(!current.isEmpty()){
                    String del;
                    del = current.substring(current.length()-1, current.length()-1);
                    current = current.substring(0, current.length() - 1);
                    if(current.isEmpty()){
                        current="0";
                    }
                }
                screen.setText(current);
            }
        });

        result = findViewById(R.id.button_result);
        result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (finish) ;
                else {
                    String expression = screen.getText().toString();
                    try {
                        Expression e = new Expression(expression);
                        double resultValue = e.calculate();

                        // 결과값이 NaN인 경우 처리
                        if (Double.isNaN(resultValue)) {
                            screen.setText(expression + "=ERR");
                        } else {
                            screen.setText(expression + "=" + resultValue);
                        }
                    } catch (Exception e) {
                        screen.setText(expression + "=ERR");
                    }
                    finish = true;
                }
            }

        });


        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(this);
        button0 = findViewById(R.id.button0);
        button0.setOnClickListener(this);


        plus=findViewById(R.id.button_plus);
        plus.setOnClickListener(this);
        minus=findViewById(R.id.button_minus);
        minus.setOnClickListener(this);
        mul=findViewById(R.id.button_mul);
        mul.setOnClickListener(this);
        div=findViewById(R.id.button_div);
        div.setOnClickListener(this);


        ddot = findViewById(R.id.button_dot);
        ddot.setOnClickListener(this);




    }

    public void onClick(View v){
        String current =screen.getText().toString();
        boolean lastChar = !current.isEmpty() && "+-*/".contains(current.substring(current.length() - 1));
        boolean lastzero = current.endsWith("0") && !current.equals("0") && lastChar;


        if(v.getId() == R.id.button1){
            if(current.equals("0")){
                current ="1";
            }
            else if(lastzero){
                current = current.substring(0, current.length() - 1) + "1";
            }
            else{current +="1";}

            ac = false;
        }
        else if(v.getId() == R.id.button2){
            if(current.equals("0")){
                current ="2";
            }
            else{current +="2";}
            ac = false;
        }
        else if(v.getId() == R.id.button3){
            if(current.equals("0")){
                current ="3";
            }
            else{current +="3";}
            ac = false;
        }
        else if(v.getId() == R.id.button4){
            if(current.equals("0")){
                current ="4";
            }
            else{current +="4";}
            ac = false;
        }
        else if(v.getId() == R.id.button5){
            if(current.equals("0")){
                current ="5";
            }
            else{current +="5";}
            ac = false;
        }
        else if(v.getId() == R.id.button6){
            if(current.equals("0")){
                current ="6";
            }
            else{current +="6";}
            ac = false;
        }
        else if(v.getId() == R.id.button7){
            if(current.equals("0")){
                current ="7";
            }
            else{current +="7";}
            ac = false;
        }
        else if(v.getId() == R.id.button8){
            if(current.equals("0")){
                current ="8";
            }
            else{current +="8";}
            ac = false;
        }
        else if(v.getId() == R.id.button9){
            if(current.equals("0")){
                current ="9";
            }
            else{current +="9";}
            ac = false;
        }
        else if(v.getId() == R.id.button0) {
            if (current.equals("0"));
            else{
                current+="0";
            }
            ac = false;
        }



        else if(v.getId() == R.id.button_plus) {
            if (ac);
            else if(lastChar);
            else {
                current += "+";
                dot = false;
            }
        }
        else if(v.getId() == R.id.button_minus) {
            if (ac);
            else if(lastChar);
            else {
                current += "-";
                dot = false;
            }
        }
        else if(v.getId() == R.id.button_mul) {
            if (ac);
            else if(lastChar);
            else {
                current += "*";
                dot = false;
            }
        }
        else if(v.getId() == R.id.button_div) {
            if (ac);
            else if(lastChar);
            else {
                current += "/";
                dot = false;
            }
        }
        else if(v.getId() == R.id.button_dot) {
            if (ac);
            else if(dot);
            else if(lastChar);
            else {
                current += ".";
                dot = true;
            }
        }



        screen.setText(current);
    }
}