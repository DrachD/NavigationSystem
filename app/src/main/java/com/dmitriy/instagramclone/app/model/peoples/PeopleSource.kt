package com.dmitriy.instagramclone.app.model.peoples

import com.dmitriy.instagramclone.sources.peoples.entities.*
import retrofit2.Response

interface PeopleSource {

    suspend fun getListUsers(page: Int): Response<ListUsersResponse>
    suspend fun getSingleUser(userId: Int): Response<SingleUserResponse>
    suspend fun addUser(createUserRequest: CreateUserRequest): Response<CreateUserResponse>
    suspend fun updateUser(userId: Int, updateUserResponse: UpdateUserRequest): Response<UpdateUserResponse>
    suspend fun deleteUser(userId: Int): Response<Unit>
}