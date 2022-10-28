package com.dmitriy.instagramclone.app.model.peoples

import com.dmitriy.instagramclone.app.model.peoples.entities.UserInfo
import com.dmitriy.instagramclone.sources.peoples.entities.*
import retrofit2.Response

class PeopleRepository(
    private val peopleSource: PeopleSource
) {

    suspend fun getSingleUser(userId: Int): Response<SingleUserResponse> {
        return peopleSource.getSingleUser(userId)
    }

    suspend fun getListUsers(page: Int): Response<ListUsersResponse> {
        return peopleSource.getListUsers(page)
    }

    suspend fun addUser(userInfo: UserInfo): Response<CreateUserResponse> {
        userInfo.validate()
        val createUserRequest = CreateUserRequest(userInfo.name, userInfo.job)
        return peopleSource.addUser(createUserRequest)
    }

    suspend fun updateUser(userId: Int, userInfo: UserInfo): Response<UpdateUserResponse> {
        userInfo.validate()
        val updateUserRequest = UpdateUserRequest(userInfo.name, userInfo.job)
        return peopleSource.updateUser(userId, updateUserRequest)
    }

    suspend fun deleteUser(userId: Int): Response<Unit> {
        return peopleSource.deleteUser(userId)
    }
}