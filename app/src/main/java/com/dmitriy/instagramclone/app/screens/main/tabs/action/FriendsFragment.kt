package com.dmitriy.instagramclone.app.screens.main.tabs.action

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment(R.layout.fragment_friends) {

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recommendsButton.setOnClickListener { onRecommendsButtonPressed() }
        binding.backButton.setOnClickListener { onBackButtonPressed() }
    }

    private fun onRecommendsButtonPressed() {
        findNavController().navigate(R.id.action_friendsFragment_to_recommendsFragment)
    }

    private fun onBackButtonPressed() = findNavController().popBackStack()

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}