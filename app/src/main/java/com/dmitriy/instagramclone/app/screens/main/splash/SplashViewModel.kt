package com.dmitriy.instagramclone.app.screens.main.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitriy.instagramclone.app.Singleton
import com.dmitriy.instagramclone.app.model.accounts.UserRepository

class SplashViewModel(
    userRepository: UserRepository = Singleton.userRepository
) : ViewModel() {

    private val _stateSignInEvent = MutableLiveData<Boolean>()
    val stateSignInEvent: MutableLiveData<Boolean> = _stateSignInEvent

    init {
        _stateSignInEvent.value = userRepository.isSignIn()
    }
}