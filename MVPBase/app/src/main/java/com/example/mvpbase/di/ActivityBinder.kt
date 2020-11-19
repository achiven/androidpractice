package com.example.mvpbase.di

import com.example.mvpbase.di.ui.MainModule
import com.example.mvpbase.view.detail.DetailActivity
import com.example.mvpbase.view.main.MainActivity
import com.example.mvpbase.view.recent.RecentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


// 객체 그래프에 추가할 액티비티는 해당 액티비티를 반환하는 함수에
// @ContributeAndroidInjector 어노테이션을 추가하여 선언합니다.
@Module
abstract class ActivityBinder {
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun bindRecentActivity(): RecentActivity
}