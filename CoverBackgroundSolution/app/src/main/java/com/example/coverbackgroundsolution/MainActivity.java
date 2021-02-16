package com.example.coverbackgroundsolution;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.coverbackgroundsolution.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // https://developer.android.com/topic/libraries/view-binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        binding.buttonExecutor.setOnClickListener(v -> navController.navigate(R.id.executorFragment));
        binding.buttonJob.setOnClickListener(v -> navController.navigate(R.id.jobFragment));
        binding.buttonWork.setOnClickListener(v -> navController.navigate(R.id.workFragment));
    }
}