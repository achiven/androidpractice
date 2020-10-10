package com.example.daggerpractice;

import dagger.Component;

@Component(modules = SampleModule.class)
public interface SampleComponent {
    void inject(MainActivity activity);
}
