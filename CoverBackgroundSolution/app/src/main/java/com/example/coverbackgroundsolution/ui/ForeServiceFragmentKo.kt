package com.example.coverbackgroundsolution.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coverbackgroundsolution.CoroutineViewModel
import com.example.coverbackgroundsolution.R
import com.example.coverbackgroundsolution.databinding.FragmentForegroundServiceBinding
import kotlinx.coroutines.*

class ForeServiceFragmentKo : Fragment() {
    private lateinit var binding: FragmentForegroundServiceBinding

    private lateinit var viewModel: CoroutineViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_foreground_service, container, false)
        binding = FragmentForegroundServiceBinding.bind(view)

        return view
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CoroutineViewModel::class.java)

        viewModel.progress.observe(requireActivity(), Observer { v ->
            binding.progress.progress = v
        })

        binding.buttonStartSvc.setOnClickListener {
            // 이제 여기서 retrofit 통신을 하면 된다. todo
            coroutineScope.launch {

                for (idx in 0..100) {

                    // Main thread에서 돌려야 한다.
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.progress.value = idx
                    }

                    delay(50)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        job.cancel()
    }

    public fun example(){

    }
}