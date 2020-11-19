package com.example.mvpbase.view.main

import android.util.Log
import com.example.mvpbase.constant.Constant
import com.example.mvpbase.model.GithubApi
import com.example.mvpbase.model.GithubApiProvider
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDao
import com.example.mvpbase.rxEventBus.RxBus
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(): MainContract.Present {

    private var api = GithubApiProvider.provideGithubApi()
    private var view: MainContract.View? = null
    private var disposable = CompositeDisposable()
    private var userDao: UserDao? = null


    constructor(userDao: UserDao, api: GithubApi?):this(){
        disposable = CompositeDisposable()
        this.userDao = userDao
        this.api = api!!
    }

    override fun setView(view: MainContract.View) {
        this.view = view              // view라고 해서 ViewGroup을 가지는게 아니고 그냥 Interface이다.
        // view는 Activity의 함수를 호출 하게 해주는 역할만 할 뿐이다.
        // 다만 개발자가 논리적으로, view를 이용해서 Activity의 화면만 변화게 사용할 뿐이다.
    }

    override fun releaseView() {
        disposable.clear()
    }


    override fun loadData() {

        disposable.add(api.getUserList(Constant.BASE_URL + Constant.RANDOM_USER_URL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showProgress() }
            .doOnTerminate { view?.hidePregress() }
            .subscribe(
                {
                    view?.setItems(it?.userList as ArrayList<User>)
                },
                { error -> view?.showToast(error.message!!) })
        )
    }

    override fun addUser(userDao: UserDao, user: User) {
        disposable.add(
            Observable.just(user)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d("MyTag", "item : $it 저장")
                        userDao.add(it)
                    },
                    {
                        Log.d("MyTag", "onError ${it.message}")

                    },
                    {Log.d("MyTag", "onCompleted")}
                )
        )
    }

    override fun setRxEvent() {

        disposable.add(
            RxBus.listen(User::class.java).subscribe(
                {
                    view!!.updateView(it)

                },
                { Log.d("MyTag", "onError") },
                { Log.d("MyTag", "onCompleted") }
            )
        )


//        disposable.add(
//            RxEvent.instance
//            !!.observable
//                .subscribe(
//                    {
//                        if (it is User) {
//                            view!!.updateView(it)
//                        }
//                    },  // 아래 코드는 생략 가능 합니다.
//                    { error -> Log.d("MyTag", "onError") },
//                    { Log.d("MyTag", "onCompleted") }
//                )
//        )

    }
}