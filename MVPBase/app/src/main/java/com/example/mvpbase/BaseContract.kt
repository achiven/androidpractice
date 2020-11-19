package com.example.mvpbase

abstract class BaseContract {       // interface can be possible

    // View의 공용 함수가 필요한 경우 정해줍니다.
    interface View{

    }

    // View와 Presenter는 1:1로 연결되기 때문에 뷰의 연결과 해제를 위한 함수를 정해줍니다.
    interface Presenter<T>{
        fun setView(view :T)

        fun releaseView()
    }


}