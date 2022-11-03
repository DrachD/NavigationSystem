package com.dmitriy.instagramclone.app.screens.main.tabs.home

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.adapter.ListUsersAdapter
import com.dmitriy.instagramclone.app.model.peoples.entities.UserData
import com.dmitriy.instagramclone.app.screens.main.MainActivity
import com.dmitriy.instagramclone.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListUsersAdapter
    private val viewModel by viewModels<HomeViewModel>()

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.home_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.addUserHome -> {
                    val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
                    val navController = navHost.navController
                    navController.navigate(R.id.action_tabsFragment_to_addUserFragment)
                }
            }
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getListUsers(1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initMenuProvider()
        observeGetSingleUser()
    }

    private fun observeGetSingleUser() = viewModel.getListUsersEvent.observe(viewLifecycleOwner) { listUsers ->
        adapter.diffAsync.submitList(listUsers.data)
    }

    private fun initRecyclerView() {
        adapter = ListUsersAdapter(requireActivity() as MainActivity) {
            navigateToUserDetails(it)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = this@HomeFragment.adapter
        }
    }

    private fun initMenuProvider() {
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun navigateToUserDetails(userData: UserData) {
        findNavController().navigate(
            R.id.action_homeFragment_to_userDetailsFragment,
            bundleOf("USER_DATA" to userData)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}