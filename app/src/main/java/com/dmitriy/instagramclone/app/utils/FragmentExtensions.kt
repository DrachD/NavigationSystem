package com.dmitriy.instagramclone.app.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.dmitriy.instagramclone.R

fun Fragment.findTopNavController(): NavController {
    val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
    return navHost?.navController ?: findNavController()
}