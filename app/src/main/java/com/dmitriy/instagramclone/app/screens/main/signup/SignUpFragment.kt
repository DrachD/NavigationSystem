package com.dmitriy.instagramclone.app.screens.main.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.model.accounts.entities.SignUpData
import com.dmitriy.instagramclone.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener { onSignUpButtonPressed() }
        binding.backButton.setOnClickListener { onBackButtonPressed() }

        observeStateProgress()
        observeShowExceptionEvent(view)
    }

    private fun observeStateProgress() = viewModel.stateProgress.observe(viewLifecycleOwner) { progress ->

        if (progress) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

        binding.backButton.isClickable = !progress
        binding.signUpButton.isClickable = !progress
    }

    private fun observeShowExceptionEvent(view: View) = viewModel.showExceptionInfoEvent.observe(viewLifecycleOwner) { info ->

        if (info == null) return@observe

        info.screenMassage?.let { Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show() }
        Log.d("logs", "code: ${info.code}; message: ${info.message}; cause: ${info.cause}")
    }

    private fun onSignUpButtonPressed() {
        val signUpData = SignUpData(
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            repeatPassword = binding.repeatPasswordEditText.text.toString()
        )
        viewModel.signUp(signUpData)
    }

    private fun onBackButtonPressed() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}