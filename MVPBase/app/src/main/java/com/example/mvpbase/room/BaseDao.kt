package com.example.mvpbase.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(entity: Array<T>)

    @Update
    fun update(entity: T)

    @Delete
    fun delete(entity: T)
}