package com.dmitriy.instagramclone.app.screens.main.tabs.home.user

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.screens.main.tabs.home.HomeViewModel
import com.dmitriy.instagramclone.databinding.FragmentUserAddBinding
import com.google.android.material.snackbar.Snackbar

class AddUserFragment : Fragment(R.layout.fragment_user_add) {

    private var _binding: FragmentUserAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.user_add_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.confirmUserAdd -> {
                    onAddUserButtonPressed()
                }
            }
            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenuProvider()
        observeAddUserEvent(view)
    }

    private fun initMenuProvider() {
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun onAddUserButtonPressed() {
        val name = binding.nameEditText.text.toString()
        val job = binding.jobEditText.text.toString()
        viewModel.addUser(name, job)
    }

    private fun observeAddUserEvent(view: View) = viewModel.addUserEvent.observe(viewLifecycleOwner) { createUserResponse ->

        Snackbar.make(view, "Пользователь ${createUserResponse.name} успешно создан", Snackbar.LENGTH_SHORT).show()
        Log.d("logs", "Пользователь успешно создан")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}