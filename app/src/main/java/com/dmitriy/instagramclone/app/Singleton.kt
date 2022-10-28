package com.dmitriy.instagramclone.app

import android.content.Context
import com.dmitriy.instagramclone.app.model.accounts.UserRepository
import com.dmitriy.instagramclone.app.model.peoples.PeopleRepository
import com.dmitriy.instagramclone.app.model.settings.SharedPreferencesToken
import com.dmitriy.instagramclone.sources.SourceProviderHolder

object Singleton {

    private lateinit var appContext: Context

    private val sourceProviderHolder: SourceProviderHolder by lazy {
        SourceProviderHolder
    }

    val sharedPreferencesToken: SharedPreferencesToken by lazy {
        SharedPreferencesToken(
            appContext
        )
    }

    // ----- repositories -----

    val userRepository: UserRepository by lazy {
        UserRepository(
            userSource = sourceProviderHolder,
            tokenSource = sharedPreferencesToken
        )
    }

    val peopleRepository: PeopleRepository by lazy {
        PeopleRepository(
            peopleSource = sourceProviderHolder
        )
    }

    // ----- initialization at application startup -----

    fun init(context: Context) {
        appContext = context
    }
}