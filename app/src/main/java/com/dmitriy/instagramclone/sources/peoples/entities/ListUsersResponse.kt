package com.dmitriy.instagramclone.sources.peoples.entities

import com.dmitriy.instagramclone.app.model.peoples.entities.UserData

data class ListUsersResponse(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<UserData>
)