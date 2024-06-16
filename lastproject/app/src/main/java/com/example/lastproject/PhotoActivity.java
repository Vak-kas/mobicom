package com.example.lastproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private List<Photo> photoList;
    private String folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Intent intent = getIntent();
        folderName = intent.getStringExtra("folderName");

        recyclerView = findViewById(R.id.photo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(photoList);
        recyclerView.setAdapter(photoAdapter);

        Button addPhotoButton = findViewById(R.id.add_photo_button);
        addPhotoButton.setOnClickListener(v -> openImageChooser());

        loadPhotos(); // 폴더에서 사진 로드
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "사진 선택"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            photoList.add(new Photo(imageUri.toString()));
            photoAdapter.notifyDataSetChanged();
            savePhotoToFolder(imageUri); // 폴더에 사진 저장
        }
    }

    private void loadPhotos() {
        // 폴더에서 사진 로드하는 로직 구현
    }

    private void savePhotoToFolder(Uri imageUri) {
        // 폴더에 사진 저장하는 로직 구현
    }
}
