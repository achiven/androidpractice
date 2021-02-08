package com.example.coverbackgroundsolution.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coverbackgroundsolution.MainViewModel;
import com.example.coverbackgroundsolution.R;
import com.example.coverbackgroundsolution.databinding.FragmentExecutorBinding;

public class ExecutorFragment extends Fragment {

    private static final String TAG = ExecutorFragment.class.getSimpleName();
    private MainViewModel viewModel;
    private FragmentExecutorBinding binding;        // Binding name rule에 의해서 "layout 이름" + "Binding" 이름으로 생김


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_executor, container, false);
        binding = FragmentExecutorBinding.bind(view);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(
                requireActivity(),          // owner가 activity이기에 fragment가 재생성 되어도 데이터는 유지된다.
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(MainViewModel.class);

        // getViewLifecycleOwner() : this 대신에 현재 view가 활성화 될 때만 활성화 되도록 자동으로 처리
        viewModel.progressLiveData.observe(getViewLifecycleOwner(), progressValue -> {
            binding.progress.setProgress(progressValue);
        });

        binding.button.setOnClickListener(v -> viewModel.longTask());
    }
}