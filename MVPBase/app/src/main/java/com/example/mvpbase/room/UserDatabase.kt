package com.example.mvpbase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvpbase.model.User

// 데이터 베이스에서 사용하는 엔티티와 버전을 지정합니다.
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    // 데이터베이스와 연결할 데이터 접근 객체를 정의합니다.
    abstract fun getUserDao() : UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "recent.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}

