package com.example.week10_hw1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week10_hw1.databinding.ActivityMainBinding;
import com.example.week10_hw1.ui.home.MyGraphicView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // 초기 선택 설정
        navView.setSelectedItemId(R.id.navigation_home);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);

                if (currentFragment != null && currentFragment.getView() != null) {
                    MyGraphicView myGraphicView = currentFragment.getView().findViewById(R.id.myGraphicView);

                    if (myGraphicView != null) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.navigation_home) {
                            myGraphicView.setShape(MyGraphicView.Shape.CIRCLE);
                        } else if (itemId == R.id.navigation_dashboard) {
                            myGraphicView.setShape(MyGraphicView.Shape.LINE);
                        } else if (itemId == R.id.navigation_notifications) {
                            myGraphicView.setShape(MyGraphicView.Shape.RECTANGLE);
                        }
                    }
                }
                return true;
            }
        });

        // 초기 도형 설정
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main);

        if (currentFragment != null && currentFragment.getView() != null) {
            MyGraphicView myGraphicView = currentFragment.getView().findViewById(R.id.myGraphicView);

            if (myGraphicView != null) {
                myGraphicView.setShape(MyGraphicView.Shape.CIRCLE);
            }
        }
    }
}