package com.dmitriy.instagramclone.app.screens.main.tabs.action

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.databinding.FragmentActionBinding

class ActionFragment : Fragment(R.layout.fragment_action) {

    private var _binding: FragmentActionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.friendsButton.setOnClickListener { onFriendsButtonPressed() }
    }

    private fun onFriendsButtonPressed() {
        findNavController().navigate(R.id.action_actionFragment_to_friendsFragment)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}