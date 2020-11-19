package com.example.mvpbase.view.main

import com.example.mvpbase.BaseContract
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDao

interface MainContract{

    interface View : BaseContract.View{
        fun showProgress()

        fun hidePregress()

        fun showToast(message : String)

        // 아이템을 어댑터에 연결해 줍니다.
        fun setItems(items : ArrayList<User>)

        // 단일 아이템에 변경되었음을 알려줍니다.
        fun updateView(user:User)


    }

    interface Present : BaseContract.Presenter<View>{

        override fun setView(view: View)

        override fun releaseView()

        // API 통신을 통해 데이터를 받아옵니다.
        fun loadData()

        // RxEventBus 를 연결하여 Like 값을 동기화 해줍니다.
        fun setRxEvent()

        // 유저 추가를 해줍니다.
        fun addUser(userDao: UserDao, user: User)

    }




}