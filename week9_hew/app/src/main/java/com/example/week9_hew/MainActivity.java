package com.example.week9_hew;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    EditText edtDiary;
    Button btnWrite;
    String fileName;

    Switch changepw;
    boolean isLocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 일기장");

        dp = findViewById(R.id.datePicker1);
        edtDiary = findViewById(R.id.edtDiary);
        btnWrite = findViewById(R.id.btnWrite);
        changepw = findViewById(R.id.changePw);


        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName = year + "_" + (monthOfYear + 1) + "_" + dayOfMonth + ".txt";
                String str = readDiary(fileName);
                edtDiary.setText(str);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    FileOutputStream outFs = openFileOutput(fileName, MODE_PRIVATE);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), fileName + "이 저장됨", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });





        loadPreferences();

        changepw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        if (!isLocked) {
                            showSetPasswordDialog();
                        }
                    } else {
                        if (isLocked) {
                            showUnlockDialog();
                        }
                    }
                }
            }
        });


    }

    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences("DiaryLockPrefs", MODE_PRIVATE);
        isLocked = prefs.getBoolean("isLocked", false);
        changepw.setChecked(isLocked);
        edtDiary.setVisibility(isLocked ? View.INVISIBLE : View.VISIBLE);
    }

    private void showSetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("암호를 변경하세요");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newPassword = input.getText().toString();
            savePassword(newPassword);
            isLocked = true;
            edtDiary.setVisibility(View.INVISIBLE);
            changepw.setChecked(true);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
            changepw.setChecked(false); // 스위치를 원래 상태로 유지
        });

        builder.show();
    }


    private void showUnlockDialog() {
        SharedPreferences prefs = getSharedPreferences("DiaryLockPrefs", MODE_PRIVATE);
        String savedPassword = prefs.getString("password", "");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("암호를 입력하세요");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputHashedPassword = hashPassword(input.getText().toString());
            if (inputHashedPassword.equals(savedPassword)) {
                isLocked = false;
                edtDiary.setVisibility(View.VISIBLE);
                changepw.setChecked(false);
                saveSwitchState(false); // 스위치 상태를 OFF로 저장
            } else {
                Toast.makeText(MainActivity.this, "암호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                changepw.setChecked(true);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
            changepw.setChecked(true); // 스위치를 원래 상태로 유지
        });

        builder.show();
    }

    private void saveSwitchState(boolean isLocked) {
        SharedPreferences prefs = getSharedPreferences("DiaryLockPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLocked", isLocked);
        editor.apply();
    }

    private void savePassword(String password) {
        String hashedPassword = hashPassword(password);  // 비밀번호를 해시 함수로 처리
        SharedPreferences prefs = getSharedPreferences("DiaryLockPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", hashedPassword);
        editor.putBoolean("isLocked", true);
        editor.apply();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = new String(txt).trim();
            btnWrite.setText("수정 하기");
        } catch (IOException e) {
            edtDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }
        return diaryStr;
    }
}
