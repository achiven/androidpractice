package com.example.mvpbase.view.recent

import com.example.mvpbase.BaseContract
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDao

interface RecentContract {
    interface View : BaseContract.View {
        fun showProgress()
        fun hideProgress()

        // Room 으로 부터 받은 데이터를 어댑터를 통해 보여줍니다.
        fun setItems(items: ArrayList<User>)
    }

    interface Present : BaseContract.Presenter<View> {
        override fun releaseView()
        override fun setView(view: View)

        // Room 으로 부터 데이터를 받아 옵니다.
        fun loadData(userDao: UserDao)

        // Room 데이터베이스에서 user 데이터를 1개 삭제 합니다.
        fun deleteData(userDao: UserDao, user: User)

        // Room 데이터를 전부 삭제 합니다.
        fun clearAll(userDao: UserDao)

    }
}