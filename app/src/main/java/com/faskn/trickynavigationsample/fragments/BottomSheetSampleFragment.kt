package com.faskn.trickynavigationsample.fragments

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.faskn.trickynavigationsample.BaseBottomSheetFragment
import com.faskn.trickynavigationsample.R
import com.faskn.trickynavigationsample.TimerFormatter
import com.faskn.trickynavigationsample.databinding.FragmentBottomSheetSampleBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetSampleFragment : BaseBottomSheetFragment() {

    private var _binding: FragmentBottomSheetSampleBinding? = null
    private val binding get() = _binding!!

    val timerTotal = 600000L // 10 Min
    lateinit var timer: CountDownTimer

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = object : CountDownTimer(timerTotal, 1000) {
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.textViewCounter.text =
                    TimerFormatter(millisUntilFinished).getRemainingTimeText()
            }
        }.start()

        binding.buttonAddAnotherFragment.setOnClickListener {
            findNavController().navigate(R.id.action_bottomSheetSampleFragment_self)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
        _binding = null
    }
}