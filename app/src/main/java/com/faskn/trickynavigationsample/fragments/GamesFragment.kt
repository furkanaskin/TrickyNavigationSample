package com.faskn.trickynavigationsample.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.faskn.trickynavigationsample.TimerFormatter
import com.faskn.trickynavigationsample.databinding.FragmentGamesBinding
import com.faskn.trickynavigationsample.popBackStackAllInstances

class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonReplaceFragment.setOnClickListener {
            findNavController().navigate(
                GamesFragmentDirections.actionGamesFragmentToDynamicTitleFragment(
                    "Replace"
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val navController = findNavController()
            if (navController.currentBackStackEntry?.destination?.id != null) {
                findNavController().popBackStackAllInstances(
                    navController.currentBackStackEntry?.destination?.id!!,
                    true
                )
            } else
                navController.popBackStack()
        }
    }
}