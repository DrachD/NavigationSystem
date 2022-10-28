package com.dmitriy.instagramclone.app.model.settings

import android.content.Context

class SharedPreferencesToken(
    private val context: Context
) : TokenSource {

    private val pref = context.getSharedPreferences(SHARED_ACCOUNT_TOKEN, Context.MODE_PRIVATE)

    override fun setToken(token: String?) {
        val editor = pref.edit()
        editor.putString(PREF_ACCOUNT_TOKEN, token)
        editor.apply()
    }

    override fun getToken(): String? {
        return pref.getString(PREF_ACCOUNT_TOKEN, null)
    }

    companion object {
        private const val PREF_ACCOUNT_TOKEN = "prefToken"
        private const val SHARED_ACCOUNT_TOKEN = "token"
    }
}