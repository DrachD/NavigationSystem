package com.dmitriy.instagramclone.app.screens.main.tabs.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dmitriy.instagramclone.app.Singleton
import com.dmitriy.instagramclone.app.model.EmptyFieldException
import com.dmitriy.instagramclone.app.model.peoples.PeopleRepository
import com.dmitriy.instagramclone.app.model.peoples.entities.UserInfo
import com.dmitriy.instagramclone.app.screens.base.BaseViewModel
import com.dmitriy.instagramclone.sources.peoples.entities.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val peopleRepository: PeopleRepository = Singleton.peopleRepository
) : BaseViewModel() {

    private val _getSingleUserEvent = MutableLiveData<SingleUserResponse>()
    val getSingleUserEvent: MutableLiveData<SingleUserResponse> = _getSingleUserEvent

    private val _getListUsersEvent = MutableLiveData<ListUsersResponse>()
    val getListUsersEvent: MutableLiveData<ListUsersResponse> = _getListUsersEvent

    private val _addUserEvent = MutableLiveData<CreateUserResponse>()
    val addUserEvent: MutableLiveData<CreateUserResponse> = _addUserEvent

    private val _deleteUserEvent = MutableLiveData<Unit>()
    val deleteUserEvent: MutableLiveData<Unit> = _deleteUserEvent

    private val _updateUserEvent = MutableLiveData<UpdateUserResponse>()
    val updateUserEvent: MutableLiveData<UpdateUserResponse> = _updateUserEvent

    fun getSingleUser(userId: Int) = viewModelScope.launch {
        val response = peopleRepository.getSingleUser(userId)
        if (response.isSuccessful) {
            _getSingleUserEvent.value = response.body()
        }
    }

    fun getListUsers(page: Int) = viewModelScope.launch {
        val response = peopleRepository.getListUsers(page)
        if (response.isSuccessful) {
            Log.d("logs", "getListUsers isSuccessful")
            _getListUsersEvent.value = response.body()
        } else {
            Log.d("logs", "getListUsers error")
        }
    }

    // Add User
    fun addUser(name: String, job: String) = viewModelScope.launch {
        val userInfo = UserInfo(name, job)
        try {
            val response = peopleRepository.addUser(userInfo)
            if (response.isSuccessful) {
                _addUserEvent.value = response.body()
            }
        } catch (e: EmptyFieldException) {

        }
    }

    // Delete User
    fun deleteUser(userId: Int) = viewModelScope.launch {
        val response = peopleRepository.deleteUser(userId)
        if (response.isSuccessful) {
            _deleteUserEvent.value = response.body()
        }
    }

    // Update User
    fun updateUser(userId: Int, name: String, job: String) = viewModelScope.launch {
        val userInfo = UserInfo(name, job)
        try {
            val response = peopleRepository.updateUser(userId, userInfo)
            if (response.isSuccessful) {
                _updateUserEvent.value = response.body()
            }
        } catch (e: Exception) {

        }
    }
}