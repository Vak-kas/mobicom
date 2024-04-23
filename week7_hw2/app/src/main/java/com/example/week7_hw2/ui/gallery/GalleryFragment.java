package com.example.week7_hw2.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.week7_hw2.R;
import com.example.week7_hw2.databinding.FragmentGalleryBinding;
import com.example.week7_hw2.databinding.FragmentSlideshowBinding;
import com.example.week7_hw2.ui.slideshow.SlideshowViewModel;
import com.google.android.material.navigation.NavigationView;

public class GalleryFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        android.app.AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());

        dlg.setTitle("좋아하는 버전은?");
        dlg.setIcon(R.mipmap.ic_launcher);


        View dialogView;

        dialogView = (View) View.inflate(getActivity(), R.layout.dialog_signin, null);
        dlg.setTitle("사용자 입력");
        dlg.setIcon(R.mipmap.ic_launcher);

        dlg.setView(dialogView);

        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        dlg.show();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}