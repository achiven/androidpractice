package com.example.mvpbase.di.ui;

import com.example.mvpbase.adapter.MainAdapter;
import com.example.mvpbase.room.UserDao;
import com.example.mvpbase.view.recent.RecentActivity;
import com.example.mvpbase.view.recent.RecentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RecentModule {

    @Provides
    MainAdapter provideMainAdapter() {
        return new MainAdapter();
    }

    @Provides
    RecentPresenter provideRecentPresenter(RecentActivity activity, UserDao userDao) {
        RecentPresenter presenter = new RecentPresenter(userDao);
        presenter.setView(activity);
        return presenter;
    }
}
