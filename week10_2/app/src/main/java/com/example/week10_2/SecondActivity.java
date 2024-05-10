package com.example.week10_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    EditText edit1, edit2;
    private ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.second);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.d("seo", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("seo", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("seo", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("seo", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("seo", "onResume");
    }

    public void backClick(View v){
        finish();
    }


    public void SecondClick(View v){
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("name", "ThisIsBundleName");
//        int num[] = {1, 2, 3, 4};
//        bundle.putIntArray("Values", num);
//        intent.putExtra("Bundle", bundle);

        intent.putExtra("name", "MinJae");
        intent.putExtra("num1", Integer.parseInt(edit1.getText().toString()));
        intent.putExtra("num2", Integer.parseInt(edit2.getText().toString()));
        startActivity(intent);
//        startActivityForResult(intent, 12);
//        resultLauncher.launch(intent);
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (data != null && requestCode ==12 && resultCode == RESULT_OK) {
//            int result = data.getIntExtra("resultvalue", 0);
//            Toast.makeText(this, "Result: " + result + " Request Code=:" + requestCode + "ResultCode=" + resultCode, Toast.LENGTH_SHORT).show();
//        }
//
//    }
//    resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if (result.getResultCode()==RESULT_OK) {
//                int resultvalue = result.getData().getIntExtra("resultvalue", 0);
//                Toast.makeText(getApplicationContext(), "Result: " + resultvalue , Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//    });



}
