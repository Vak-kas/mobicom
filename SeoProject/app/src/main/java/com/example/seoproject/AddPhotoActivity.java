package com.example.seoproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class AddPhotoActivity extends AppCompatActivity {

    private ImageView selectedImageView;
    private LinearLayout tagContainer;
    private EditText tagEditText1, tagEditText2, tagEditText3;
    private Uri selectedImageUri;
    private FolderDbHelper dbHelper;
    private long folderId;
    private String savedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        selectedImageView = findViewById(R.id.selected_image_view);
        tagContainer = findViewById(R.id.tag_container);
        tagEditText1 = findViewById(R.id.tag_edit_text_1);
        tagEditText2 = findViewById(R.id.tag_edit_text_2);
        tagEditText3 = findViewById(R.id.tag_edit_text_3);
        Button addTagButton = findViewById(R.id.add_tag_button);
        Button savePhotoButton = findViewById(R.id.save_photo_button);

        dbHelper = new FolderDbHelper(this);

        Intent intent = getIntent();
        selectedImageUri = intent.getData();
        folderId = intent.getLongExtra("folderId", -1);

        if (selectedImageUri != null) {
            selectedImageView.setImageURI(selectedImageUri);
            saveImageToLocal(selectedImageUri);
        }

        tagEditText1.setVisibility(View.VISIBLE);

        addTagButton.setOnClickListener(v -> addTagField());
        savePhotoButton.setOnClickListener(v -> savePhoto());
    }

    private void addTagField() {
        if (tagEditText2.getVisibility() == View.GONE) {
            tagEditText2.setVisibility(View.VISIBLE);
        } else if (tagEditText3.getVisibility() == View.GONE) {
            tagEditText3.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "최대 3개의 태그만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageToLocal(Uri imageUri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(imageUri);
            if (inputStream != null) {
                File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyApp");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File file = new File(directory, System.currentTimeMillis() + ".jpg");
                OutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();

                savedImagePath = file.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePhoto() {
        String tag1 = tagEditText1.getText().toString().trim();
        String tag2 = tagEditText2.getText().toString().trim();
        String tag3 = tagEditText3.getText().toString().trim();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FolderDbHelper.PHOTO_COLUMN_IMAGE_URI, savedImagePath);
        values.put(FolderDbHelper.PHOTO_COLUMN_TAG1, tag1.isEmpty() ? null : tag1);
        values.put(FolderDbHelper.PHOTO_COLUMN_TAG2, tag2.isEmpty() ? null : tag2);
        values.put(FolderDbHelper.PHOTO_COLUMN_TAG3, tag3.isEmpty() ? null : tag3);
        values.put(FolderDbHelper.PHOTO_COLUMN_FOLDER_ID, folderId);

        long newRowId = db.insert(FolderDbHelper.PHOTO_TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "사진 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
