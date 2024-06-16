package com.example.seoproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhotoActivity extends AppCompatActivity implements TagAdapter.OnTagClickListener, PhotoAdapter.OnPhotoActionListener {

    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_CODE_ADD_PHOTO = 2;

    private TextView folderNameTextView;
    private TextView emptyFolderTextView;
    private RecyclerView photoRecyclerView;
    private RecyclerView tagRecyclerView;
    private PhotoAdapter photoAdapter;
    private TagAdapter tagAdapter;
    private List<Photo> photoList;
    private List<String> tagList;
    private Set<String> selectedTags;
    private FolderDbHelper dbHelper;
    private long folderId;
    private boolean isShareMode = false;
    private String currentFilterMode = "MATCH_ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        folderNameTextView = findViewById(R.id.folder_name_text_view);
        emptyFolderTextView = findViewById(R.id.empty_folder_text_view);
        photoRecyclerView = findViewById(R.id.photo_recycler_view);
        tagRecyclerView = findViewById(R.id.tag_recycler_view);
        Button addPhotoButton = findViewById(R.id.add_photo_button);
        Button sharePhotoButton = findViewById(R.id.share_photo_button);
        Button filterButton = findViewById(R.id.filter_button);
        Button resetButton = findViewById(R.id.reset_button);

        dbHelper = new FolderDbHelper(this);

        Intent intent = getIntent();
        String folderName = intent.getStringExtra("folderName");
        folderId = getFolderIdByName(folderName);
        folderNameTextView.setText(folderName);

        photoList = new ArrayList<>();
        tagList = new ArrayList<>();
        selectedTags = new HashSet<>();

        photoAdapter = new PhotoAdapter(photoList, this);
        tagAdapter = new TagAdapter(tagList, this);

        photoRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        photoRecyclerView.setAdapter(photoAdapter);

        tagRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tagRecyclerView.setAdapter(tagAdapter);

        loadPhotosFromDatabase(folderName);

        addPhotoButton.setOnClickListener(v -> selectImage());
        sharePhotoButton.setOnClickListener(v -> toggleShareMode());

        filterButton.setOnClickListener(v -> showFilterMenu(filterButton));
        resetButton.setOnClickListener(v -> resetFilters());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPhotosFromDatabase(folderNameTextView.getText().toString());
        filterPhotosByTags();
    }

    private void showFilterMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this::onFilterMenuItemClick);
        popupMenu.show();
    }

    private boolean onFilterMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_match_all) {
            currentFilterMode = "MATCH_ALL";
            resetFilters();
            ((Button) findViewById(R.id.filter_button)).setText("모두 일치");
            return true;
        } else if (itemId == R.id.action_match_any) {
            currentFilterMode = "MATCH_ANY";
            resetFilters();
            ((Button) findViewById(R.id.filter_button)).setText("일부 일치");
            return true;
        } else {
            return false;
        }
    }

    private void toggleShareMode() {
        isShareMode = !isShareMode;
        if (isShareMode) {
            for (PhotoAdapter.PhotoViewHolder holder : getAllViewHolders(photoRecyclerView)) {
                holder.photoCheckBox.setVisibility(View.VISIBLE);
            }
        } else {
            if (!photoAdapter.getSelectedPhotos().isEmpty()) {
                shareSelectedPhotos();
            }
            for (PhotoAdapter.PhotoViewHolder holder : getAllViewHolders(photoRecyclerView)) {
                holder.photoCheckBox.setVisibility(View.GONE);
                holder.photoCheckBox.setChecked(false);
            }
            photoAdapter.clearSelectedPhotos();
        }
    }

    private void shareSelectedPhotos() {
        List<Uri> fileUris = new ArrayList<>();
        for (Uri uri : photoAdapter.getSelectedPhotos()) {
            File file = new File(uri.getPath());
            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            fileUris.add(fileUri);
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setType("image/*");
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, new ArrayList<>(fileUris));
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(shareIntent, "공유하기"));
    }

    private List<PhotoAdapter.PhotoViewHolder> getAllViewHolders(RecyclerView recyclerView) {
        List<PhotoAdapter.PhotoViewHolder> holders = new ArrayList<>();
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            holders.add((PhotoAdapter.PhotoViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i)));
        }
        return holders;
    }

    private void loadPhotosFromDatabase(String folderName) {
        photoList.clear();
        tagList.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = FolderDbHelper.PHOTO_COLUMN_FOLDER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(folderId) };

        Cursor cursor = db.query(FolderDbHelper.PHOTO_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_IMAGE_URI));
            String tag1 = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_TAG1));
            String tag2 = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_TAG2));
            String tag3 = cursor.getString(cursor.getColumnIndexOrThrow(FolderDbHelper.PHOTO_COLUMN_TAG3));

            photoList.add(new Photo(imageUri, tag1, tag2, tag3));

            if (tag1 != null && !tag1.isEmpty() && !tagList.contains(tag1)) tagList.add(tag1);
            if (tag2 != null && !tag2.isEmpty() && !tagList.contains(tag2)) tagList.add(tag2);
            if (tag3 != null && !tag3.isEmpty() && !tagList.contains(tag3)) tagList.add(tag3);
        }
        cursor.close();

        if (photoList.isEmpty()) {
            emptyFolderTextView.setVisibility(View.VISIBLE);
            photoRecyclerView.setVisibility(View.GONE);
        } else {
            emptyFolderTextView.setVisibility(View.GONE);
            photoRecyclerView.setVisibility(View.VISIBLE);
        }

        tagAdapter.notifyDataSetChanged();
        photoAdapter.notifyDataSetChanged();
    }

    private long getFolderIdByName(String folderName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { FolderDbHelper.COLUMN_ID };
        String selection = FolderDbHelper.COLUMN_NAME + " = ?";
        String[] selectionArgs = { folderName };
        Cursor cursor = db.query(FolderDbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            long folderId = cursor.getLong(cursor.getColumnIndexOrThrow(FolderDbHelper.COLUMN_ID));
            cursor.close();
            return folderId;
        }

        if (cursor != null) {
            cursor.close();
        }

        return -1;
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Intent intent = new Intent(this, AddPhotoActivity.class);
            intent.setData(selectedImage);
            intent.putExtra("folderId", folderId);
            startActivityForResult(intent, REQUEST_CODE_ADD_PHOTO);
        } else if (requestCode == REQUEST_CODE_ADD_PHOTO && resultCode == RESULT_OK) {
            loadPhotosFromDatabase(folderNameTextView.getText().toString());
            filterPhotosByTags();
        }
    }

    @Override
    public void onTagClick(String tag) {
        if (selectedTags.contains(tag)) {
            selectedTags.remove(tag);
        } else {
            selectedTags.add(tag);
        }
        tagAdapter.notifyDataSetChanged();
        filterPhotosByTags();
    }

    @Override
    public Set<String> getSelectedTags() {
        return selectedTags;
    }

    private void filterPhotosByTags() {
        List<Photo> filteredPhotos = new ArrayList<>();

        if (currentFilterMode.equals("MATCH_ALL")) {
            for (Photo photo : photoList) {
                if (selectedTags.isEmpty() || photo.getTags().containsAll(selectedTags)) {
                    filteredPhotos.add(photo);
                }
            }
        } else if (currentFilterMode.equals("MATCH_ANY")) {
            for (Photo photo : photoList) {
                if (selectedTags.isEmpty()) {
                    filteredPhotos.add(photo);
                } else {
                    for (String tag : selectedTags) {
                        if (photo.getTags().contains(tag)) {
                            filteredPhotos.add(photo);
                            break;
                        }
                    }
                }
            }
        }

        photoAdapter.updatePhotoList(filteredPhotos);
    }

    private void resetFilters() {
        selectedTags.clear();
        tagAdapter.notifyDataSetChanged();
        filterPhotosByTags();
    }

    @Override
    public void onEditTags(Photo photo, int position) {
        showEditTagsDialog(photo, position);
    }

    @Override
    public void onDeletePhoto(Photo photo, int position) {
        showDeletePhotoDialog(photo, position);
    }

    private void showEditTagsDialog(Photo photo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("태그 수정");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_tags, null);
        EditText tagEditText1 = view.findViewById(R.id.tag_edit_text_1);
        EditText tagEditText2 = view.findViewById(R.id.tag_edit_text_2);
        EditText tagEditText3 = view.findViewById(R.id.tag_edit_text_3);

        tagEditText1.setText(photo.getTag1());
        tagEditText2.setText(photo.getTag2());
        tagEditText3.setText(photo.getTag3());

        builder.setView(view);
        builder.setPositiveButton("저장", (dialog, which) -> {
            photo.setTag1(tagEditText1.getText().toString().trim());
            photo.setTag2(tagEditText2.getText().toString().trim());
            photo.setTag3(tagEditText3.getText().toString().trim());
            updatePhotoTagsInDatabase(photo);
            loadPhotosFromDatabase(folderNameTextView.getText().toString());
            photoAdapter.notifyItemChanged(position);
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updatePhotoTagsInDatabase(Photo photo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FolderDbHelper.PHOTO_COLUMN_TAG1, photo.getTag1().isEmpty() ? null : photo.getTag1());
        values.put(FolderDbHelper.PHOTO_COLUMN_TAG2, photo.getTag2().isEmpty() ? null : photo.getTag2());
        values.put(FolderDbHelper.PHOTO_COLUMN_TAG3, photo.getTag3().isEmpty() ? null : photo.getTag3());

        String selection = FolderDbHelper.PHOTO_COLUMN_IMAGE_URI + " = ?";
        String[] selectionArgs = { photo.getImageUri() };

        db.update(FolderDbHelper.PHOTO_TABLE_NAME, values, selection, selectionArgs);
    }

    private void showDeletePhotoDialog(Photo photo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 삭제");
        builder.setMessage("사진을 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", (dialog, which) -> {
            deletePhotoFromDatabase(photo);
            photoList.remove(position);
            photoAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "사진이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            loadPhotosFromDatabase(folderNameTextView.getText().toString());
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void deletePhotoFromDatabase(Photo photo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = FolderDbHelper.PHOTO_COLUMN_IMAGE_URI + " = ?";
        String[] selectionArgs = { photo.getImageUri() };
        db.delete(FolderDbHelper.PHOTO_TABLE_NAME, selection, selectionArgs);
    }
}
