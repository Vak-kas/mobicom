package com.example.week13_2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnCall;
    private TextView edtCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, MODE_PRIVATE);

        btnCall = findViewById(R.id.btnCall);
        edtCall = findViewById(R.id.edtCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edtCall.setText(getCallHistory());
            }
        });
    }

    public String getCallHistory() {
        String[] callSet = new String[]{
                CallLog.Calls.DATE,
                CallLog.Calls.TYPE,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return "권한이 없습니다";
        }

        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI, callSet, null, null, null);

        if (c == null || c.getCount() == 0) {
            return "통화기록 없음";
        }

        StringBuilder callBuff = new StringBuilder();
        callBuff.append("\n날짜 : 구분 : 전화번호 : 통화시간\n\n");
        c.moveToFirst();
        do {
            long callDate = c.getLong(0);
            SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
            String date_str = datePattern.format(new Date(callDate));
            callBuff.append(date_str).append(":");
            if (c.getInt(1) == CallLog.Calls.INCOMING_TYPE) {
                callBuff.append("착신 :");
            } else {
                callBuff.append("발신 :");
            }
            callBuff.append(c.getString(2)).append(":");
            callBuff.append(c.getString(3)).append("초\n");
        } while (c.moveToNext());

        c.close();
        return callBuff.toString();
    }
}
