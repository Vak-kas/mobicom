package com.example.seoproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchBar;
    private RecyclerView folderRecyclerView;
    private FolderAdapter folderAdapter;
    private List<String> folderList;
    private FolderDbHelper dbHelper;
    private boolean isDeleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_bar);
        folderRecyclerView = findViewById(R.id.folder_recycler_view);
        Button addFolderButton = findViewById(R.id.add_folder);
        Button deleteFolderButton = findViewById(R.id.delete_folder);

        dbHelper = new FolderDbHelper(this);
        folderList = new ArrayList<>();
        folderAdapter = new FolderAdapter(folderList, this::onFolderClick);

        folderRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        folderRecyclerView.setAdapter(folderAdapter);

        loadFoldersFromDatabase();

        addFolderButton.setOnClickListener(v -> showAddFolderDialog());
        deleteFolderButton.setOnClickListener(v -> toggleDeleteMode());

        // Uncomment the following line to reset the database
//         resetDatabase();
    }

    private void loadFoldersFromDatabase() {
        folderList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(FolderDbHelper.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String folderName = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.COLUMN_NAME));
            folderList.add(folderName);
        }
        cursor.close();
        folderAdapter.notifyDataSetChanged();
    }

    private void showAddFolderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("폴더명 입력");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String folderName = input.getText().toString().trim();
                addFolder(folderName);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addFolder(String folderName) {
        if (!folderName.isEmpty() && !folderList.contains(folderName)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FolderDbHelper.COLUMN_NAME, folderName);
            long newRowId = db.insert(FolderDbHelper.TABLE_NAME, null, values);
            if (newRowId != -1) {
                folderList.add(folderName);
                folderAdapter.notifyDataSetChanged();
                Toast.makeText(this, "폴더가 추가되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "폴더 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "폴더 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleDeleteMode() {
        isDeleteMode = !isDeleteMode;
        folderAdapter.setShowRadioButton(isDeleteMode);
        if (!isDeleteMode) {
            folderAdapter.setSelectedPosition(-1); // Reset selection
        }
    }

    private void showDeleteFolderDialog(String folderName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("폴더 삭제");
        builder.setMessage(folderName + " 폴더를 삭제하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFolder(folderName);
                exitDeleteMode();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                exitDeleteMode();
            }
        });
        builder.show();
    }

    private void deleteFolder(String folderName) {
        if (folderList.contains(folderName)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int rowsDeleted = db.delete(FolderDbHelper.TABLE_NAME, FolderDbHelper.COLUMN_NAME + "=?", new String[]{folderName});
            if (rowsDeleted > 0) {
                folderList.remove(folderName);
                folderAdapter.notifyDataSetChanged();
                Toast.makeText(this, "폴더가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "폴더 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "존재하지 않는 폴더입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onFolderClick(String folderName) {
        if (isDeleteMode) {
            showDeleteFolderDialog(folderName);
        } else {
            Intent intent = new Intent(this, PhotoActivity.class);
            intent.putExtra("folderName", folderName);
            startActivity(intent);
        }
    }

    private void resetDatabase() {
        dbHelper.resetDatabase();
        loadFoldersFromDatabase();
    }

    private void exitDeleteMode() {
        isDeleteMode = false;
        folderAdapter.setShowRadioButton(false);
        folderAdapter.setSelectedPosition(-1); // Reset selection
    }
}
