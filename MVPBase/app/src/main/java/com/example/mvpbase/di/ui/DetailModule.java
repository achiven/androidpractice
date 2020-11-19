package com.example.mvpbase.di.ui;


import com.example.mvpbase.view.detail.DetailActivity;
import com.example.mvpbase.view.detail.DetailPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailModule {

    @Provides
    DetailPresenter provideDetailPresenter(DetailActivity activity) {
        DetailPresenter presenter = new DetailPresenter();
        presenter.setView(activity);
        return presenter;
    }
}
