package com.example.week9_1;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_STORAGE = 100;
    EditText ed;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        checkPermission();

        ed = (EditText) findViewById(R.id.EditText);
        imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.an);

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

    public void externalSaveClick(View v) {


        File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");   //
        myDir.mkdir();

        Log.d("hwang", myDir.toString());

        try {
            FileOutputStream outFs = new FileOutputStream(new File(myDir, "external.txt"));
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
            Log.d("hwang", "external save ok");
        } catch (IOException e) {
            Log.d("hwang", "external save error" + e.toString());
        }
    }

    public void externalLoadClick(View v) {
        try {

            File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");
            FileInputStream inFs = new FileInputStream(new File(myDir, "external.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
            Log.d("hwang", "external load error" + e.toString());
        }

    }

    public void copyClick(View v) {
        // internal to external //
/*
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            FileInputStream inFs = openFileInput("internal.txt");
            FileOutputStream outFs = new FileOutputStream(new File(strSDpath + "/mydir", "internal.txt"));  //permission
            int c;
            while ((c = inFs.read()) != -1)
                outFs.write(c);

            outFs.close();
            inFs.close();
        } catch (IOException e) {
        }
        */
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String strSDpath2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();

        Log.d("hwang", strSDpath + "   public path= " + strSDpath2);
        try {

            InputStream inFs = getResources().openRawResource(R.raw.an);

            FileOutputStream outFs = new FileOutputStream(new File(strSDpath, "ancopy.jpg"));
            FileOutputStream outFs2 = new FileOutputStream(new File(strSDpath2, "ancopy.jpg"));
            int c;
            while ((c = inFs.read()) != -1) {
                outFs.write(c);
                outFs2.write(c);
            }

            outFs.close();
            outFs2.close();
            inFs.close();
        } catch (IOException e) {
            Log.d("hwang", "error" + e.toString());
        }

    }


    public void deleteClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "ancopy.jpg");

        file.delete();


    }

    public void showimageClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "ancopy.jpg");
        imageView.setImageURI(Uri.fromFile(file));
    }


    private void checkPermission() {

        //Log.d("hwang","check=" + checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);
            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            //Log.e(TAG, "permission deny");
            //writeFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,   permissions,   grantResults);

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                    Toast.makeText(this, "권한 승인", Toast.LENGTH_SHORT).show();
                    Log.d("hwang", "Permission granted");

                } else {
                    Log.d("hwang", "Permission deny");
                    // permission denied,
                }
                break;
        }
    }

}

