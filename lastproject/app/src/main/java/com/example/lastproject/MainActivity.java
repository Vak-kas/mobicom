package com.example.lastproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView folderRecyclerView;
    private FolderAdapter folderAdapter;
    private List<Folder> folderList;
    private EditText searchBar;
    private Button addFolderButton, deleteFolderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_bar);
        folderRecyclerView = findViewById(R.id.folder_recycler_view);
        addFolderButton = findViewById(R.id.add_folder);
        deleteFolderButton = findViewById(R.id.delete_folder);

        folderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        folderList = new ArrayList<>();
        folderList.add(new Folder("김영빈 갤러리"));
        folderList.add(new Folder("40 학생회"));

        folderAdapter = new FolderAdapter(folderList, this::onFolderClick);
        folderRecyclerView.setAdapter(folderAdapter);

        addFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFolder();
            }
        });

        deleteFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFolder();
            }
        });
    }

    private void onFolderClick(Folder folder) {
//        Intent intent = new Intent(this, PhotoActivity.class);
//        intent.putExtra("folderName", folder.getName());
//        startActivity(intent);
    }

    private void addFolder() {
        // 폴더 추가 로직 구현
    }

    private void deleteFolder() {
        // 폴더 삭제 로직 구현
    }
}