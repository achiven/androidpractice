package com.example.mvpbase.di

import android.content.Context
import androidx.room.Room
import com.example.mvpbase.room.UserDao
import com.example.mvpbase.room.UserDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideUserDao(db: UserDatabase): UserDao{
        return db.getUserDao()
    }

    // 데이터 베이스를 관리하는 객체인 UserDatabase를 제공합니다.
    // "appContext"라는 이름으로 구분되는 Context 객체를 필요로 합니다.

    @Provides
    @Singleton
    fun provideUserDatabase(@Named("appContext") context: Context): UserDatabase{
        return Room.databaseBuilder(context, UserDatabase::class.java,
                "black_jib.db")
            .build()
    }
}