package com.example.mvpbase.view.detail

import com.example.mvpbase.BaseContract
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDao

interface DetailContract {
    interface Present : BaseContract.Presenter<View> {
        override fun setView(view: View)
        override fun releaseView()
        fun clickEvent(userDao: UserDao, user: User)
    }

    interface View : BaseContract.View {
        fun setText(text: String)
    }
}