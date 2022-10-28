package com.dmitriy.instagramclone.app.model.accounts.entities

import com.dmitriy.instagramclone.app.model.EmptyFieldException

data class SignInData(
    val email: String,
    val password: String
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException()
        if (password.isBlank()) throw EmptyFieldException()
    }
}