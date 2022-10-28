package com.dmitriy.instagramclone.app.screens.main.tabs.home.user

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.model.peoples.entities.UserData
import com.dmitriy.instagramclone.app.screens.main.tabs.home.HomeViewModel
import com.dmitriy.instagramclone.databinding.FragmentUserUpdateBinding
import com.google.android.material.snackbar.Snackbar
import com.dmitriy.instagramclone.app.utils.getBaseParcelable

class UpdateUserFragment : Fragment(R.layout.fragment_user_update) {

    private var _binding: FragmentUserUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private var data: UserData? = null

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.user_update_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.confirmUserUpdate -> {
                    onAddUserButtonPressed()
                }
            }
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = arguments?.getBaseParcelable<UserData>("USER_DATA", UserData::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenuProvider()
        observeUpdateUser(view)
    }

    private fun initMenuProvider() {
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun observeUpdateUser(view: View) = viewModel.updateUserEvent.observe(viewLifecycleOwner) {

        Snackbar.make(view, "Пользователь ${it.name} успешно обновлен", Snackbar.LENGTH_SHORT).show()
        Log.d("logs", "Пользователь успешно обновлен")
    }

    private fun onAddUserButtonPressed() {
        val name = binding.nameEditText.text.toString()
        val job = binding.jobEditText.text.toString()
        viewModel.updateUser(data!!.id, name, job)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}