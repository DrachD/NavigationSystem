package com.dmitriy.instagramclone.sources.peoples

import com.dmitriy.instagramclone.sources.peoples.entities.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("api/users")
    suspend fun getListUsers(@Query("page") page: Int): Response<ListUsersResponse>

    @GET("api/user/{id}")
    suspend fun getSingleUser(@Path("id") userId: Int): Response<SingleUserResponse>

    @POST("api/users")
    suspend fun addUser(
        @Body createUserRequest: CreateUserRequest
    ): Response<CreateUserResponse>

    @PUT("api/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: Int,
        @Body updateUserResponse: UpdateUserRequest
    ): Response<UpdateUserResponse>

    @DELETE("api/users/{id}")
    suspend fun deleteUser(@Path("id") userId: Int): Response<Unit>
}