package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String TB_STUDENT = "tb_student";
    public static final String TB_GRADE = "tb_grade";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_ID = "_id";


    public DBHelper(Context context){
        super(context, "studentdb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 최초 DB가 이용될 때 딱 한번 실행된다.
        // Table create
        String studentSql= "create table " + TB_STUDENT + " ( " + COLUMN_ID + " integer primary key autoincrement," +
                COLUMN_NAME + " not null," +
                COLUMN_EMAIL + "," +
                COLUMN_PHONE + "," +
                COLUMN_PHOTO + ")";

        String scoreSql = "create table " + TB_GRADE + " (" +
                COLUMN_ID + " integer primary key autoincrement," +
                "student_id not null," +
                "subject," +
                "date," +
                "grade)";

        sqLiteDatabase.execSQL(studentSql);
        sqLiteDatabase.execSQL(scoreSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // version이 올라 갈 때만 호출 된다.
        // schema 변경 목적으로 이용

        if(newVersion == DATABASE_VERSION){
            // for developing
            db.execSQL("drop table " + TB_STUDENT);
            db.execSQL("drop table " + TB_GRADE);
            onCreate(db);
        }
    }


    public boolean deleteOne(StudentVO studentVO){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "delete from " + TB_STUDENT + " where " + COLUMN_ID + " = " + studentVO.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        }else{
            cursor.close();
            db.close();
            return false;
        }

    }
}
