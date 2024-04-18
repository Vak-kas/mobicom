package com.example.week6;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    Button btnstart, btnstop;

    TextView textView;
//    CalendarView calanderView;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btnstart = (Button) findViewById(R.id.buttonstart);
        btnstop = (Button) findViewById(R.id.buttonstop);


        //calanderView= (CalendarView) findViewById(R.id.calendarView);
        textView = (TextView) findViewById(R.id.textview);

        datePicker = (DatePicker) findViewById(R.id.datepicker);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(new String().format("year: %d, month: %d, day:%d", year, monthOfYear+1, dayOfMonth));

            }
        });


//        calanderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
  //          @Override
    //        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
      //          textView.setText(new String().format("year: %d, month: %d, day:%d", year, month+1, dayOfMonth));
        //    }
        //});



        btnstart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometer.setTextColor(Color.rgb(0, 255, 0));
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chronometer.stop();
                chronometer.setTextColor(Color.rgb(255, 0, 0));
            }
        });



    }
}