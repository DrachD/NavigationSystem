package com.dmitriy.instagramclone.app.model.peoples.entities

import com.dmitriy.instagramclone.app.model.EmptyFieldException

data class UserInfo(
    val name: String,
    val job: String
) {
    fun validate() {
        if (name.isBlank()) throw EmptyFieldException()
        if (job.isBlank()) throw EmptyFieldException()
    }
}