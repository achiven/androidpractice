package com.example.mvpbase.di.ui;


import com.example.mvpbase.adapter.MainAdapter;
import com.example.mvpbase.model.GithubApi;
import com.example.mvpbase.room.UserDao;
import com.example.mvpbase.view.main.MainActivity;
import com.example.mvpbase.view.main.MainPresenter;

import dagger.Module;
import dagger.Provides;









//@Module
//public class MainModule {
//    @Provides
//    MainAdapter provideMainAdapter(MainActivity activity) {
//        MainAdapter adapter = new MainAdapter();
//        adapter.setClickListener(activity);
//        return adapter;
//    }
//
//    @Provides
//    MainPresenter provideMainPresenter(MainActivity activity, UserDao userDao, GithubApi api) {
//        MainPresenter presenter = new MainPresenter(userDao, api);
//        presenter.setView(activity);
//        return presenter;
//    }
//}
