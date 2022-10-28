package com.dmitriy.instagramclone.app.model.accounts

import com.dmitriy.instagramclone.sources.accounts.entities.SignInRequest
import com.dmitriy.instagramclone.sources.accounts.entities.SignInResponse
import com.dmitriy.instagramclone.sources.accounts.entities.SignUpRequest
import com.dmitriy.instagramclone.sources.accounts.entities.SignUpResponse

interface UserSource {

    suspend fun signUp(user: SignUpRequest): SignUpResponse
    suspend fun signIn(user: SignInRequest): SignInResponse
}