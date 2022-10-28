package com.dmitriy.instagramclone.sources.peoples.entities

data class CreateUserResponse(
    val name: String,
    val job: String,
    val id: String,
    val createdAt: String
)