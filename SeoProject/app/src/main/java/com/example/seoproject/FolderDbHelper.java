package com.example.seoproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FolderDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "folders.db"; // 이름이 바뀜
    private static final int DATABASE_VERSION = 3;  // 데이터베이스 버전을 3으로 올림

    public static final String TABLE_NAME = "folders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public static final String PHOTO_TABLE_NAME = "photos";
    public static final String PHOTO_COLUMN_ID = "_id";
    public static final String PHOTO_COLUMN_FOLDER_ID = "folder_id";
    public static final String PHOTO_COLUMN_IMAGE_URI = "image_uri";
    public static final String PHOTO_COLUMN_TAG1 = "tag1";
    public static final String PHOTO_COLUMN_TAG2 = "tag2";
    public static final String PHOTO_COLUMN_TAG3 = "tag3";
    public static final String PHOTO_COLUMN_TIMESTAMP = "timestamp";  // 추가된 부분
    public static final String PHOTO_COLUMN_SHARE_COUNT = "share_count";  // 추가된 부분

    public FolderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOLDER_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL);";
        String CREATE_PHOTO_TABLE = "CREATE TABLE " + PHOTO_TABLE_NAME + " ("
                + PHOTO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PHOTO_COLUMN_FOLDER_ID + " INTEGER, "
                + PHOTO_COLUMN_IMAGE_URI + " TEXT, "
                + PHOTO_COLUMN_TAG1 + " TEXT, "
                + PHOTO_COLUMN_TAG2 + " TEXT, "
                + PHOTO_COLUMN_TAG3 + " TEXT, "
                + PHOTO_COLUMN_TIMESTAMP + " INTEGER, "  // 추가된 부분
                + PHOTO_COLUMN_SHARE_COUNT + " INTEGER DEFAULT 0, "  // 추가된 부분
                + "FOREIGN KEY(" + PHOTO_COLUMN_FOLDER_ID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "));";
        db.execSQL(CREATE_FOLDER_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + PHOTO_TABLE_NAME + " ADD COLUMN " + PHOTO_COLUMN_TIMESTAMP + " INTEGER;");
            db.execSQL("ALTER TABLE " + PHOTO_TABLE_NAME + " ADD COLUMN " + PHOTO_COLUMN_SHARE_COUNT + " INTEGER DEFAULT 0;");
        }
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PHOTO_TABLE_NAME);
        onCreate(db);
    }

    public void deleteFolderAndPhotos(String folderName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 폴더 ID 가져오기
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID}, COLUMN_NAME + "=?", new String[]{folderName}, null, null, null);
        if (cursor.moveToFirst()) {
            long folderId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));

            // 해당 폴더의 모든 사진 삭제
            db.delete(PHOTO_TABLE_NAME, PHOTO_COLUMN_FOLDER_ID + "=?", new String[]{String.valueOf(folderId)});

            // 폴더 삭제
            db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(folderId)});
        }
        cursor.close();
    }
}
