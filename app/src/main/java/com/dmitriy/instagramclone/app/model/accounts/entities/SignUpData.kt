package com.dmitriy.instagramclone.app.model.accounts.entities

import com.dmitriy.instagramclone.app.model.EmptyFieldException
import com.dmitriy.instagramclone.app.model.PasswordMismatchException

data class SignUpData(
    val email: String,
    val password: String,
    val repeatPassword: String
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException()
        if (password.isBlank()) throw EmptyFieldException()
        if (repeatPassword.isBlank()) throw EmptyFieldException()
        if (password != repeatPassword) throw PasswordMismatchException()
    }
}