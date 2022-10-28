package com.dmitriy.instagramclone.sources.peoples.entities

import com.dmitriy.instagramclone.app.model.peoples.entities.UserData

data class SingleUserResponse(
    val data: UserData,
    val support: Support
) {

    data class Support(
        val url: String,
        val text: String
    )
}