package com.dmitriy.instagramclone.app.screens.main.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dmitriy.instagramclone.app.Singleton
import com.dmitriy.instagramclone.app.model.*
import com.dmitriy.instagramclone.app.model.accounts.UserRepository
import com.dmitriy.instagramclone.app.model.accounts.entities.SignUpData
import com.dmitriy.instagramclone.app.screens.base.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userRepository: UserRepository = Singleton.userRepository
) : BaseViewModel() {

    private val _stateProgress = MutableLiveData<Boolean>()
    val stateProgress: MutableLiveData<Boolean> = _stateProgress

    fun signUp(signUpData: SignUpData) = viewModelScope.launch {
        showProgress()
        try {
            userRepository.signUp(signUpData)
            showExceptionInfo(null, null, null, "Пользователь успешно создан!")
        } catch (e: EmptyFieldException)  {
            showExceptionInfo(null, e.message, e.cause, "Пустое поле ввода!")
        } catch(e: PasswordMismatchException) {
            showExceptionInfo(null, e.message, e.cause, "Пароли не совпадают!")
        } catch (e: ConnectionException) {
            showExceptionInfo(null, e.message, e.cause, "Ошибка соединения с сервером!")
        } catch (e: BackendException) {
            if (e.code == 400) {
                showExceptionInfo(e.code, e.message, e.cause, "Пустое поле ввода для пароля!")
            } else {
                showExceptionInfo(e.code, e.message, e.cause, "Ошибка сервера!")
            }
        } catch (e: AccountAlreadyExistsException) {
            showExceptionInfo(null, e.message, e.cause, "аккаунт уже существует!")
        }
        catch (e: Exception) {
            showExceptionInfo(null, e.message, e.cause, "Что-то случилось. Пожалуйста, повторите снова позже!")
        } finally {
            hideProgress()
            hideExceptionInfo()
        }
    }

    private fun showProgress() { _stateProgress.value = true }
    private fun hideProgress() { _stateProgress.value = false }
}