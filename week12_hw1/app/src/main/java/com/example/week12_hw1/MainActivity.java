package com.example.week12_hw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    myDBHelper myHelper;
    EditText edtName, edtNumber, edtId;
    TextView textViewResult;
    Button btnInit, btnInsert, btnSelect, btnUpdate, btnDelete;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("가수 그룹 관리 DB");

        edtName = findViewById(R.id.editText_name);
        edtNumber = findViewById(R.id.editText_number);
        edtId = findViewById(R.id.editText_id);
        textViewResult = findViewById(R.id.textView_result);
        btnInit = findViewById(R.id.button_reset);
        btnInsert = findViewById(R.id.button_add);
        btnSelect = findViewById(R.id.button_view);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);

        myHelper = new myDBHelper(this);

        btnInit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetDatabase();
            }
        });

        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void resetDatabase() {
        sqlDB = myHelper.getWritableDatabase();
        myHelper.resetDatabase(sqlDB);
        sqlDB.close();
        Toast.makeText(MainActivity.this, "Database Reset", Toast.LENGTH_SHORT).show();
        resetFields();
    }

    public void resetFields() {
        edtName.setText("");
        edtNumber.setText("");
        edtId.setText("");
        textViewResult.setText("");
    }

    public void AddData() {
        btnInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isInserted = myHelper.insertData(edtName.getText().toString(), edtNumber.getText().toString());
                if (isInserted)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewAll() {
        btnSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor res = myHelper.getAllData();
                if (res.getCount() == 0) {
                    textViewResult.setText("No data found.");
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("id : ").append(res.getString(0)).append("\n")
                            .append("이름 : ").append(res.getString(1)).append("\n")
                            .append("인원 : ").append(res.getString(2)).append("\n\n");
                }

                textViewResult.setText(buffer.toString());
            }
        });
    }

    public void UpdateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isUpdate = myHelper.updateData(edtId.getText().toString(), edtName.getText().toString(), edtNumber.getText().toString());
                if (isUpdate)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer deletedRows = myHelper.deleteData(edtId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "group.db";
        public static final String TABLE_NAME = "groupTBL";
        public static final String COL_1 = "ID";
        public static final String COL_2 = "NAME";
        public static final String COL_3 = "MARKS";

        public myDBHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, MARKS INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void resetDatabase(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertData(String name, String marks) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, name);
            contentValues.put(COL_3, marks);
            long result = db.insert(TABLE_NAME, null, contentValues);
            return result != -1;
        }

        public Cursor getAllData() {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }

        public boolean updateData(String id, String name, String marks) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1, id);
            contentValues.put(COL_2, name);
            contentValues.put(COL_3, marks);
            int result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
            return result > 0;
        }

        public Integer deleteData(String id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        }
    }
}
