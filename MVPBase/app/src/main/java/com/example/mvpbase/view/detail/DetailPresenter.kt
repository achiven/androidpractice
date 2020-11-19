package com.example.mvpbase.view.detail

import android.util.Log
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDao
import com.example.mvpbase.rxEventBus.RxBus
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailPresenter : DetailContract.Present{

    private lateinit var view : DetailContract.View
    private var disposable = CompositeDisposable()

    override fun setView(view: DetailContract.View) {
        this.view = view
    }

    override fun releaseView() {
        disposable.clear()
    }

    override fun clickEvent(userDao: UserDao, user: User) {
        user.linkCnt++

        view.setText(user.getLikeCnt())

//      RxEventBus 를 호출하여 MainActivity 에 변화가 생겼음을 알려줍니다.
        RxBus.publish(user)
//      RxEvent.instance.sendEvent(user)

        disposable.add(
            Observable.just(user)
                .subscribeOn(Schedulers.io())
                .subscribe{
                    Log.d("MyTag", "item : $it 업데이트")
                    userDao.update(user)
                }
        )

//        Coroutine 로도 가능
//        CoroutineScope(Dispatchers.Default).launch {
//            userDao.update(user)
//        }.start()


    }
}
