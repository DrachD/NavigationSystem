package com.dmitriy.instagramclone.app.screens.main.tabs.home.user

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.model.peoples.entities.UserData
import com.dmitriy.instagramclone.app.screens.main.tabs.home.HomeViewModel
import com.dmitriy.instagramclone.app.utils.findTopNavController
import com.dmitriy.instagramclone.app.utils.getBaseParcelable
import com.dmitriy.instagramclone.databinding.FragmentUserDetailsBinding
import com.google.android.material.snackbar.Snackbar

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private var data: UserData? = null

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.user_details_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.updateUserDetails -> {
                    onUpdateUserButtonPressed()
                }
                R.id.deleteUserDetails -> {
                    onDeleteUserButtonPressed()
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
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = arguments?.getBaseParcelable<UserData>("USER_DATA", UserData::class.java)

        binding.apply {
            nicknameTextView.text = "${data?.first_name} ${data?.last_name}"
            emailTextView.text = data?.email
        }

        initMenuProvider()
        observeDeleteser(view)
    }

    private fun observeDeleteser(view: View) = viewModel.deleteUserEvent.observe(viewLifecycleOwner) {

        Snackbar.make(view, "Пользователь успешно удален", Snackbar.LENGTH_SHORT).show()
        Log.d("logs", "Пользователь успешно удален")
    }

    private fun initMenuProvider() {
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun onUpdateUserButtonPressed() {
        findTopNavController().navigate(R.id.action_tabsFragment_to_updateUserFragment, bundleOf("USER_DATA" to data))
    }

    private fun onDeleteUserButtonPressed() {
        viewModel.deleteUser(data!!.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}