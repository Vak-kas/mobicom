package com.example.week9_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ed = (EditText) findViewById(R.id.EditText);

    }

    public void sharedPreferencesSaveClick(View v) {

        SharedPreferences settings = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", ed.getText().toString());
        editor.commit();
    }

    public void sharedPreferencesLoadClick(View v) {

        SharedPreferences settings = getSharedPreferences("shared", MODE_PRIVATE);
        String str = settings.getString("name", "");
        ed.setText(str);
    }

    public void clearText(View v){
        ed.setText("");
    }


    public void internalSaveClick(View v) {
        try {
            //OutputStream이 파일에 글 쓰기
            FileOutputStream outFs = openFileOutput("internal.txt", MODE_PRIVATE);
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void internalLoadClick(View v) {
        try {
            //InputStream이 파일에 글 읽기
            FileInputStream inFs = openFileInput("internal.txt");
            //   FileInputStream inFs=new  FileInputStream(new File(getFilesDir(), "internal.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
        }
    }

}

