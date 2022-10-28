package com.dmitriy.instagramclone.sources.accounts

import com.dmitriy.instagramclone.sources.accounts.entities.SignInRequest
import com.dmitriy.instagramclone.sources.accounts.entities.SignInResponse
import com.dmitriy.instagramclone.sources.accounts.entities.SignUpRequest
import com.dmitriy.instagramclone.sources.accounts.entities.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("api/register")
    suspend fun signUp(@Body user: SignUpRequest): SignUpResponse

    @POST("api/login")
    suspend fun signIn(@Body user: SignInRequest): SignInResponse
}