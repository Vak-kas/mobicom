package com.example.seoproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.OnPhotoActionListener {

    private EditText searchBar;
    private RecyclerView folderRecyclerView;
    private RecyclerView searchRecyclerView;
    private FolderAdapter folderAdapter;
    private PhotoAdapter photoAdapter;
    private List<String> folderList;
    private List<Photo> searchResults;
    private FolderDbHelper dbHelper;
    private boolean isDeleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_bar);
        folderRecyclerView = findViewById(R.id.folder_recycler_view);
        searchRecyclerView = findViewById(R.id.search_result_recycler_view);
        Button addFolderButton = findViewById(R.id.add_folder);
        Button deleteFolderButton = findViewById(R.id.delete_folder);
        Button searchButton = findViewById(R.id.search_button);

        dbHelper = new FolderDbHelper(this);
        folderList = new ArrayList<>();
        searchResults = new ArrayList<>();
        folderAdapter = new FolderAdapter(folderList, this::onFolderClick);
        photoAdapter = new PhotoAdapter(searchResults, this);
        photoAdapter.setSearchMode(true); // 검색 모드 활성화

        folderRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        folderRecyclerView.setAdapter(folderAdapter);

        searchRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        searchRecyclerView.setAdapter(photoAdapter);

        loadFoldersFromDatabase();

        addFolderButton.setOnClickListener(v -> showAddFolderDialog());
        deleteFolderButton.setOnClickListener(v -> toggleDeleteMode());
        searchButton.setOnClickListener(v -> searchPhotos());

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

    private void searchPhotos() {
        String searchText = searchBar.getText().toString().trim();
        searchResults.clear();

        if (!searchText.isEmpty()) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selection = FolderDbHelper.PHOTO_COLUMN_TAG1 + " LIKE ? OR " +
                    FolderDbHelper.PHOTO_COLUMN_TAG2 + " LIKE ? OR " +
                    FolderDbHelper.PHOTO_COLUMN_TAG3 + " LIKE ?";
            String[] selectionArgs = new String[]{"%" + searchText + "%", "%" + searchText + "%", "%" + searchText + "%"};

            Cursor cursor = db.query(FolderDbHelper.PHOTO_TABLE_NAME, null, selection, selectionArgs, null, null, null);
            while (cursor.moveToNext()) {
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_IMAGE_URI));
                String tag1 = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_TAG1));
                String tag2 = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_TAG2));
                String tag3 = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_TAG3));

                searchResults.add(new Photo(imageUri, tag1, tag2, tag3));
            }
            cursor.close();

            if (searchResults.isEmpty()) {
                Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
            }

            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onEditTags(Photo photo, int position) {
        // MainActivity에서 태그 수정 기능을 사용할 필요가 없다면 구현하지 않습니다.
    }

    @Override
    public void onDeletePhoto(Photo photo, int position) {
        // MainActivity에서 사진 삭제 기능을 사용할 필요가 없다면 구현하지 않습니다.
    }

    @Override
    public void onSharePhoto(Uri photoUri) {
        File file = new File(photoUri.getPath());
        Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "공유하기"));
    }
}
