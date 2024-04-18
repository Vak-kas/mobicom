package com.example.week6_3;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editurl;
    Button btngo, btnback;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editurl = (EditText) findViewById(R.id.editText);
        btngo = (Button) findViewById(R.id.btngo);
        btnback = (Button) findViewById(R.id.btnback);
        web = (WebView) findViewById(R.id.webView);

        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl("https://" +editurl.getText().toString());
            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.goBack();
            }
        });

    }
}