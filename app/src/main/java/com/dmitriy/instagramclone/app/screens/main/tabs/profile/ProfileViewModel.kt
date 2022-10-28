package com.dmitriy.instagramclone.app.screens.main.tabs.profile

import androidx.lifecycle.ViewModel
import com.dmitriy.instagramclone.app.Singleton
import com.dmitriy.instagramclone.app.model.accounts.UserRepository

class ProfileViewModel(
    private val userRepository: UserRepository = Singleton.userRepository
) : ViewModel() {

    fun logout() {
        userRepository.logout()
    }
}