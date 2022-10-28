package com.dmitriy.instagramclone.app.screens.main.tabs.action

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.databinding.FragmentRecommendsBinding

class RecommendsFragment : Fragment(R.layout.fragment_recommends) {

    private var _binding: FragmentRecommendsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener { onBackButtonPressed() }
        binding.actionButton.setOnClickListener { onActionButtonPressed() }
    }

    private fun onBackButtonPressed() = findNavController().popBackStack()
    private fun onActionButtonPressed() {
        findNavController().popBackStack(R.id.actionFragment, false)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}