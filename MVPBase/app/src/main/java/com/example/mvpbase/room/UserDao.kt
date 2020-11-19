package com.example.mvpbase.room

import androidx.room.*
import com.example.mvpbase.model.User
import io.reactivex.Flowable


// 데이터 접근 객체를 생성합니다.
@Dao
interface UserDao: BaseDao<User> {

    // 저장되어 있는 저장소 목록을 반환합니다.
    // Flowable 형태의 자료를 반환하므로, 데이터베이스가 변경되면 알림을 받아 새로운 자료를 가져옵니다.
    // 따라서 항상 최신 자료를 유지합니다.
    @Query("SELECT * FROM userTable")
    fun getUser(): Flowable<List<User>>

    // repositories 테이블의 모든 데이터를 삭제 합니다.
    @Query("DELETE FROM userTable")
    fun clearAll()
}