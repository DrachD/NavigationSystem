package com.dmitriy.instagramclone.app.model.settings

interface TokenSource {

    fun setToken(token: String?)
    fun getToken(): String?
}