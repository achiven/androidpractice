package com.example.mvpbase.di.ui

import com.example.mvpbase.adapter.MainAdapter
import com.example.mvpbase.model.GithubApi
import com.example.mvpbase.room.UserDao
import com.example.mvpbase.view.main.MainActivity
import com.example.mvpbase.view.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainAdapter(activity: MainActivity): MainAdapter{
        val adapter = MainAdapter()
        adapter.setClickListener(activity)
        return adapter
    }

    @Provides
    fun provideMainPresenter(activity: MainActivity, userDao: UserDao, api: GithubApi): MainPresenter{
        val presenter = MainPresenter(userDao, api)
        presenter.setView(activity)
        return presenter
    }
}