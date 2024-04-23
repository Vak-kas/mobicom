package com.example.week7_hw.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.week7_hw.databinding.FragmentDashboardBinding;
import com.example.week7_hw.ui.home.HomeViewModel;
import com.example.week7_hw.ui.home.HomeFragment;
import com.example.week7_hw.ui.notifications.NotificationsViewModel;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private HomeViewModel homeViewModel;
    private NotificationsViewModel notificationsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button naver = binding.naver;
        Button daum = binding.daum;
        Button google = binding.google;
        naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.changeText("https://m.naver.com");
                notificationsViewModel.changeText("https://m.naver.com");
            }
        });

        daum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.changeText("https://www.daum.net");
                notificationsViewModel.changeText("https://www.daum.net");

            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.changeText("https://www.google.com");
                notificationsViewModel.changeText("https://www.google.com");

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
