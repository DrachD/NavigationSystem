package com.dmitriy.instagramclone.app.screens.main.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dmitriy.instagramclone.app.Singleton
import com.dmitriy.instagramclone.app.model.BackendException
import com.dmitriy.instagramclone.app.model.ConnectionException
import com.dmitriy.instagramclone.app.model.EmptyFieldException
import com.dmitriy.instagramclone.app.model.accounts.UserRepository
import com.dmitriy.instagramclone.app.model.accounts.entities.SignInData
import com.dmitriy.instagramclone.app.screens.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(
    private val userRepository: UserRepository = Singleton.userRepository
) : BaseViewModel() {

    private val _stateProgress = MutableLiveData<Boolean>()
    val stateProgress: MutableLiveData<Boolean> = _stateProgress

    private val _navigateToTabsEvent = MutableLiveData<Unit>()
    val navigateToTabsEvent: MutableLiveData<Unit> = _navigateToTabsEvent

    fun signIn(signInData: SignInData) = viewModelScope.launch {
        showProgress()
        try {
            userRepository.signIn(signInData)
            launchTabsFragment()
        } catch (e: EmptyFieldException)  {
            showExceptionInfo(null, e.message, e.cause, "Пустое поле ввода! signin")
        } catch (e: ConnectionException) {
            showExceptionInfo(null, e.message, e.cause, "Ошибка соединения с сервером!")
        } catch (e: BackendException) {
            if (e.code == 400) {
                showExceptionInfo(e.code, e.message, e.cause, "Пустое поле ввода для пароля!")
            } else {
                showExceptionInfo(e.code, e.message, e.cause, "Ошибка сервера!")
            }
        } catch (e: Exception) {
            showExceptionInfo(null, e.message, e.cause, "Что-то случилось. Пожалуйста, повторите снова позже!")
        } finally {
            hideProgress()
            hideExceptionInfo()
        }
    }

    private fun launchTabsFragment() {
        _navigateToTabsEvent.value = Unit
    }

    private fun showProgress() { _stateProgress.value = true }
    private fun hideProgress() { _stateProgress.value = false }
}