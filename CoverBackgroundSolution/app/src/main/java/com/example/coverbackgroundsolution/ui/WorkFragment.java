package com.example.coverbackgroundsolution.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coverbackgroundsolution.R;
import com.example.coverbackgroundsolution.databinding.FragmentWorkBinding;
import com.example.coverbackgroundsolution.worker.MyWorker;


// Service를 등록 하지 않고도 Service를 사용하는 효과를 낼 수 있다.(without declaration on Manifest.
// 다양한 conditions 를 추가 하여 사용할 수 있다.
public class WorkFragment extends Fragment {

    private FragmentWorkBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        binding = FragmentWorkBinding.bind(view);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(MyWorker.class)
                .build();

        WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(request.getId())
                .observe(getViewLifecycleOwner(), workInfo -> {
                    int progress = workInfo.getProgress().getInt("progress", 0);
                    binding.progress.setProgress(progress);         // need to use ViewModel component to retain data
                });
        binding.buttonStart.setOnClickListener(v->{

            WorkManager.getInstance(requireContext())
                    .enqueue(request);
        });
    }
}