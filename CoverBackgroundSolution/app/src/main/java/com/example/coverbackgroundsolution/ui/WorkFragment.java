package com.example.coverbackgroundsolution.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coverbackgroundsolution.R;
import com.example.coverbackgroundsolution.WorkManagerViewModel;
import com.example.coverbackgroundsolution.databinding.FragmentWorkBinding;


// Service를 등록 하지 않고도 Service를 사용하는 효과를 낼 수 있다.(without declaration on Manifest.
// 다양한 conditions 를 추가 하여 사용할 수 있다.
public class WorkFragment extends Fragment {

    private FragmentWorkBinding binding;

    private WorkManagerViewModel viewModel;

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

//        viewModel = new ViewModelProvider(requireActivity()).get(WorkManagerViewModel.class);
        viewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(WorkManagerViewModel.class);
            // because of lib version mismatched
            // https://stackoverflow.com/questions/61370134/androidviewmodel-has-no-zero-argument-constructor

        viewModel.progressLiveData.observe(getViewLifecycleOwner(), workInfo -> {
            binding.progress.setProgress(workInfo.getProgress().getInt("progress", 0));
        });

        binding.buttonStart.setOnClickListener(v -> viewModel.startLongTask());
    }
}