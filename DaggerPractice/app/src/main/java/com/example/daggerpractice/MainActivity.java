package com.example.daggerpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    @Inject
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SampleComponent로 부터 DaggerSampleComponent가 자동 생성 되므로, 그것을 사용하여 SampleComponent를 만듭니다.
        SampleComponent component = DaggerSampleComponent.builder()
                // 사용하는 Module 인스턴스를 지정합니다.
                // (여기서 deprecated 될 수 있지만, 일단 모든 코드를 작성하고 빌드하면 사라질 것입니다.)
                .sampleModule(new SampleModule())
                .build();

        // 의존 주입을 실행합니다.
        component.inject(this);

        Log.d("MainActivity", owner.getPetName());

    }
}