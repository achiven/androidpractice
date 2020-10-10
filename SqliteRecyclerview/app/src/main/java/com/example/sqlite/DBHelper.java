package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Locale;

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
        // called once for the whole application life

        // Table create
//        String studentSql= "create table " + TB_STUDENT + " ( " + COLUMN_ID + " integer primary key autoincrement," +
//                COLUMN_NAME + " not null," +
//                COLUMN_EMAIL + "," +
//                COLUMN_PHONE + "," +
//                COLUMN_PHOTO + ")";

        String studentSql = String.format(Locale.US,
                "create table %s ('%s' integer primary key autoincrement, '%s' text not null, '%s' text, '%s' text, '%s' text)",
                TB_STUDENT,
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PHONE,
                COLUMN_PHOTO
                );


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
        if(newVersion == DATABASE_VERSION){
            // for developing
            db.execSQL("drop table " + TB_STUDENT);
            db.execSQL("drop table " + TB_GRADE);
            onCreate(db);
        }
    }


    public boolean deleteOne(StudentVO studentVO){
        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "delete from " + TB_STUDENT + " where " + COLUMN_ID + " = " + studentVO.getId();
//        String queryString = "delete from tb_student where _id = " + studentVO.getId();

        String queryString = String.format(Locale.US, "delete from %s where %s = %d", TB_STUDENT, COLUMN_ID, studentVO.getId());
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        }

        cursor.close();
        db.close();
        return false;
    }
}
