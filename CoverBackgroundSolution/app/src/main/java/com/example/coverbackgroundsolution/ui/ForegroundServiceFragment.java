package com.example.coverbackgroundsolution.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coverbackgroundsolution.R;
import com.example.coverbackgroundsolution.databinding.FragmentForegroundServiceBinding;
import com.example.coverbackgroundsolution.service.ForegroundService;


public class ForegroundServiceFragment extends Fragment {

    private FragmentForegroundServiceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_foreground_service, container, false);
        binding = FragmentForegroundServiceBinding.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonStart.setOnClickListener(v -> {
//            requireActivity().startForegroundService(new Intent(requireContext(), ForegroundService.class));        // this style is available since version 23.

            ContextCompat.startForegroundService(                               // alternative for all version
                    requireContext(),
                    new Intent(requireContext(), ForegroundService.class));
        });
    }
}
