package com.example.week7_hw2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;

import com.example.week7_hw2.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.week7_hw2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.login) {
            if (login) {
                // 이미 로그인되어 있으면 로그아웃 처리
                login = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("사용자 입력");


                View dialogView = getLayoutInflater().inflate(R.layout.dialog_signin, null);
                builder.setView(dialogView);


                EditText nameEditText = dialogView.findViewById(R.id.name);
                EditText emailEditText = dialogView.findViewById(R.id.email);
                RadioGroup idolRadioGroup = dialogView.findViewById(R.id.idol);
                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView textViewName = headerView.findViewById(R.id.textViewName);
                TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);
                textViewName.setText("Android Studio");
                textViewEmail.setText("android.studio@android.com");
                ImageView imageView = headerView.findViewById(R.id.imageView);
                imageView.setImageResource(R.mipmap.ic_launcher_round);


                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("사용자 입력");

                View dialogView = getLayoutInflater().inflate(R.layout.dialog_signin, null);
                builder.setView(dialogView);

                EditText nameEditText = dialogView.findViewById(R.id.name);
                EditText emailEditText = dialogView.findViewById(R.id.email);
                RadioGroup idolRadioGroup = dialogView.findViewById(R.id.idol);
                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView textViewName = headerView.findViewById(R.id.textViewName);
                TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String enteredName = nameEditText.getText().toString();
                        String enteredEmail = emailEditText.getText().toString();

                        textViewName.setText(enteredName);
                        textViewEmail.setText(enteredEmail);


                        int selectedId = idolRadioGroup.getCheckedRadioButtonId();
                        RadioButton selectedRadioButton = dialogView.findViewById(selectedId);


                        ImageView imageView = headerView.findViewById(R.id.imageView);
                        if (selectedRadioButton.getId() == R.id.an) {
                            imageView.setImageResource(R.drawable.an);
                        } else if (selectedRadioButton.getId() == R.id.kim) {
                            imageView.setImageResource(R.drawable.kim);
                        } else if (selectedRadioButton.getId() == R.id.jang) {
                            imageView.setImageResource(R.drawable.jang);
                        }

                        login = true;
                    }
                });

                // "Cancel" 버튼 클릭 시 처리
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "cancel action", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem loginItem = menu.findItem(R.id.login);
        if (login) {
            loginItem.setTitle("로그아웃");
        } else {
            loginItem.setTitle("로그인");
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}