package com.example.daggerpractice;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = SampleModule.class)
public interface SampleComponent {
    void inject(MainActivity activity);
}
