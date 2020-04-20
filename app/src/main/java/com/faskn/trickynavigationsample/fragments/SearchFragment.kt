package com.faskn.trickynavigationsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faskn.trickynavigationsample.base.BaseBottomTabFragment
import com.faskn.trickynavigationsample.databinding.FragmentSearchBinding

class SearchFragment : BaseBottomTabFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDynamicTitleNavigate.setOnClickListener {
            navigateWithAction(
                SearchFragmentDirections.actionSearchFragmentToDynamicTitleFragment(
                    binding.editTextTitle.text.toString()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}