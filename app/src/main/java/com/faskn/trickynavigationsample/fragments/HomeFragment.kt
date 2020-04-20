package com.faskn.trickynavigationsample.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.faskn.trickynavigationsample.R
import com.faskn.trickynavigationsample.databinding.FragmentHomeBinding
import com.faskn.trickynavigationsample.utils.TimerFormatter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val timerTotal = 1200000L // 20 Min
    lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = object : CountDownTimer(timerTotal, 1000) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.textViewCounter.text =
                    TimerFormatter(
                        millisUntilFinished
                    ).getRemainingTimeText()
            }
        }.start()

        binding.buttonAddFragment.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottomSheetSampleFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
        _binding = null
    }
}
