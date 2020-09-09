package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import static com.example.sqlite.DBHelper.TB_STUDENT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button addBtn;
    Button clearDBBtn;
    RecyclerView recyclerView;
    ArrayList<StudentVO> data;
    double initTime;
    private RecyclerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);


        addBtn = findViewById(R.id.btn_go_to_addActivity);
        clearDBBtn = findViewById(R.id.btn_db_clear);

        data = new ArrayList<>();

        recyclerView = findViewById(R.id.main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerListAdapter(this, data);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                DBHelper helper = new DBHelper(MainActivity.this);
                helper.deleteOne(data.get(position));

                Handler handler = new Handler();
                final Runnable r = () -> {
                    try {
                        data.remove(position);
                        adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                };
                handler.post(r);
            }
        }).attachToRecyclerView(recyclerView);

        adapter.notifyDataSetChanged();
        addBtn.setOnClickListener(this);
        clearDBBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        if (view == addBtn) {
            Intent intent = new Intent(this, AddStudentActivity.class);
            startActivity(intent);
        } else if (view == clearDBBtn) {
            DBHelper helper = new DBHelper(this);
            helper.onUpgrade(helper.getWritableDatabase(), 1, 1);
            data.clear();
            adapter.notifyDataSetChanged();

        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        showData();

    }

    public void showData() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_STUDENT + " order by name", null);

        data.clear();
        while (cursor.moveToNext()) {
            StudentVO vo = new StudentVO();

            vo.id = cursor.getInt(0);
            vo.name = cursor.getString(1);
            vo.email = cursor.getString(2);
            vo.phone = cursor.getString(3);
            vo.photo = cursor.getString(4);

            data.add(vo);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, R.string.main_back_end, Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }

            initTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}