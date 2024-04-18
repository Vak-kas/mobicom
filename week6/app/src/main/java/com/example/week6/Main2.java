package com.example.week6;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;

public class Main2 extends AppCompatActivity {
    TimePicker timePicker;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.timepicker);


        timePicker = (TimePicker) findViewById(R.id.timepicker);
        textView = (TextView) findViewById(R.id.textView);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute){
                textView.setText(new String().format("hour: %d, minute: %d", hourOfDay, minute));
            }
        });
    }



}
