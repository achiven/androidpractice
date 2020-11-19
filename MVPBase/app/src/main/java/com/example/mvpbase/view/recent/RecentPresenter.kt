package com.example.mvpbase.view.recent

import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RecentPresenter() : RecentContract.Present {

    private lateinit var view: RecentContract.View
    private var disposable = CompositeDisposable()
    private var userDao: UserDao? = null

    override fun releaseView() {
        disposable.clear()
    }

    constructor(userDao: UserDao):this() {
        disposable = CompositeDisposable()
        this.userDao = userDao
    }

    override fun setView(view: RecentContract.View) {
        this.view = view
    }

    override fun loadData(userDao: UserDao) {
        disposable.add(
            userDao.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showProgress()
                }
                .subscribe {
                    view.setItems(it as ArrayList<User>)
                    view.hideProgress()
                }
        )


    }

    override fun deleteData(userDao: UserDao, user: User) {
        disposable.add(
            Observable.just(user)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    userDao.delete(it)
                }
        )
    }

    override fun clearAll(userDao: UserDao) {
        disposable.add(
            Observable.just("clear All")
                .subscribeOn(Schedulers.io())
                .subscribe {
                    userDao.clearAll()
                }

        )
    }
}