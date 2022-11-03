package com.dmitriy.instagramclone.app.screens.main.tabs.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.navOptions
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.utils.findTopNavController
import com.dmitriy.instagramclone.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener { onLogoutButtonPressed() }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun onLogoutButtonPressed() {

        viewModel.logout()

        findTopNavController().navigate(R.id.signInFragment, null,
            navOptions {
                popUpTo(R.id.signInFragment) {
                    inclusive = true
                }
            }
        )
    }
}