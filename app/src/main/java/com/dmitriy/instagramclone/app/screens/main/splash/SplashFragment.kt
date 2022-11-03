package com.dmitriy.instagramclone.app.screens.main.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SplashViewModel>()

    private var action: Int = getActionSignInDestination()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeStateSignInEvent()
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delayNavigate()
        }
    }
    
    private suspend fun delayNavigate() {
        delay(2000)

        findNavController().navigate(
            action,
            null,
            navOptions { popUpTo(getSplashDestination()) { inclusive = true } }
        )
    }

    private fun observeStateSignInEvent() = viewModel.stateSignInEvent.observe(viewLifecycleOwner) { isSignedIn ->
        action = if (isSignedIn) {
            getActionTabsDestination()
        } else {
            getActionSignInDestination()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun getActionTabsDestination() = R.id.action_splashFragment_to_tabsFragment
    private fun getActionSignInDestination() = R.id.action_splashFragment_to_signInFragment
    private fun getSplashDestination() = R.id.splashFragment
}