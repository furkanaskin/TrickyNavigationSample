package com.faskn.trickynavigationsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.faskn.trickynavigationsample.R
import com.faskn.trickynavigationsample.popBackStackAllInstances

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

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