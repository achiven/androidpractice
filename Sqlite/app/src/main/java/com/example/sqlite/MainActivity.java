package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import static com.example.sqlite.DBHelper.COLUMN_NAME;
import static com.example.sqlite.DBHelper.TB_STUDENT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    EditText nameView;
    EditText emailView;
    EditText phoneView;
    Button addBtn;
    ListView listView;
    ArrayList<StudentVO> data;
    double initTime;
    private MainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        addBtn = findViewById(R.id.btn_go_to_addActivity);
        listView = findViewById(R.id.main_list);


        addBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == addBtn){
            Intent intent = new Intent(this, AddStudentActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // to way to go Activity
//        Intent intent = new Intent(this, ReadStudentActivity.class);
//        intent.putExtra("id", data.get(position).id);
//        startActivity(intent);

        // to way to go delete
        StudentVO studentVO = (StudentVO) adapterView.getItemAtPosition(position);

        DBHelper helper = new DBHelper(this);
        helper.deleteOne(studentVO);

        ShowList();
        Toast.makeText(this, "Deleted " + studentVO.getId(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        ShowList();

    }

    private void ShowList() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_STUDENT + " order by name", null);

        data = new ArrayList<>();
        while(cursor.moveToNext()){
            StudentVO vo = new StudentVO();

            vo.id = cursor.getInt(0);
            vo.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));      // cursor.getString(1);
            vo.email = cursor.getString(2);
            vo.phone = cursor.getString(3);
            vo.photo = cursor.getString(4);
            data.add(vo);
        }

        cursor.close();
        db.close();

        // using custom
        adapter = new MainListAdapter(this, R.layout.main_list_item, data);
        listView.setAdapter(adapter);

        // using default
//        ArrayAdapter defaultAdapter = new ArrayAdapter<StudentVO>(MainActivity.this, android.R.layout.simple_list_item_1, data);
//        listView.setAdapter(defaultAdapter);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(this, R.string.main_back_end, Toast.LENGTH_SHORT).show();
            }else{
                finish();
            }

            initTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}