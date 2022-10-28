package com.dmitriy.instagramclone.app.screens.main.signin

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.model.accounts.entities.SignInData
import com.dmitriy.instagramclone.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener { onSignInButtonPressed() }
        binding.registeredButton.setOnClickListener { onRegisteredButtonPressed() }

        observeState()
        observeNavigateToTabsEvent()
        observeShowExceptionEvent(view)
    }

    private fun onSignInButtonPressed() {
        viewModel.signIn(
            SignInData(
                email = binding.emailEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
        )
    }

    private fun onRegisteredButtonPressed() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    private fun observeState() = viewModel.stateProgress.observe(viewLifecycleOwner) { progress ->

        binding.progressBar.visibility = if (progress) View.VISIBLE else View.GONE
        binding.signInButton.isClickable = !progress
    }

    private fun observeShowExceptionEvent(view: View) = viewModel.showExceptionInfoEvent.observe(viewLifecycleOwner) { info ->

        if (info == null) return@observe

        info.screenMassage?.let { Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show() }
        Log.d("logs", "code: ${info.code}; message: ${info.message}; cause: ${info.cause}")
    }

    private fun observeNavigateToTabsEvent() = viewModel.navigateToTabsEvent.observe(viewLifecycleOwner) {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment, null, navOptions {
            popUpTo(R.id.signInFragment) {
                inclusive = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}