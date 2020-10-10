package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import static com.example.sqlite.DBHelper.COLUMN_EMAIL;
import static com.example.sqlite.DBHelper.COLUMN_NAME;
import static com.example.sqlite.DBHelper.COLUMN_PHONE;
import static com.example.sqlite.DBHelper.TB_STUDENT;

public class AddStudentActivity extends AppCompatActivity {

    EditText nameView;
    EditText emailView;
    EditText phoneView;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        nameView = findViewById(R.id.tv_name);
        emailView = findViewById(R.id.tv_email);
        phoneView = findViewById(R.id.tv_phone);
        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameView.getText().toString();
                String email = emailView.getText().toString();
                String phone = phoneView.getText().toString();

                if(name == null || name.equals("")){
                    Toast.makeText(getApplicationContext(), "Name is empty", Toast.LENGTH_SHORT).show();
                }else{
                    DBHelper helper = new DBHelper(AddStudentActivity.this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME, name);
                    cv.put(COLUMN_EMAIL, email);
                    cv.put(COLUMN_PHONE, phone);

                    long newRowId = db.insert(TB_STUDENT, null, cv);
                    if(newRowId == -1){
                        Toast.makeText(getApplicationContext(), "An error is occurred.", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                    finish();
                }
            }
        });

    }
}