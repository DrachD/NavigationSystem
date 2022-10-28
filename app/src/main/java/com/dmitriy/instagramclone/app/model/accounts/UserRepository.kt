package com.dmitriy.instagramclone.app.model.accounts

import com.dmitriy.instagramclone.app.model.BackendException
import com.dmitriy.instagramclone.app.model.ConnectionException
import com.dmitriy.instagramclone.app.model.accounts.entities.SignInData
import com.dmitriy.instagramclone.app.model.accounts.entities.SignUpData
import com.dmitriy.instagramclone.app.model.settings.TokenSource
import com.dmitriy.instagramclone.sources.accounts.entities.SignInRequest
import com.dmitriy.instagramclone.sources.accounts.entities.SignUpRequest
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class UserRepository(
    private val userSource: UserSource,
    private val tokenSource: TokenSource
) {

    suspend fun signIn(signInData: SignInData) {
        signInData.validate()
        val signInRequest = SignInRequest(
            email = signInData.email,
            password = signInData.password
        )

        val token = try {
            userSource.signIn(signInRequest).token
        } catch (e: HttpException) {
            throw createBackendException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        }

        tokenSource.setToken(token)
    }

    suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()

        val signUpRequest = SignUpRequest(
            email = signUpData.email,
            password = signUpData.password
        )

        try {
            userSource.signUp(signUpRequest)
        } catch (e: HttpException) {
            throw createBackendException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        val errorObj = JSONObject(e.response()!!.errorBody()!!.charStream().readText())
        return BackendException(e.code(), errorObj.getString("error"))
    }

    fun logout() {
        tokenSource.setToken(null)
    }

    fun isSignIn(): Boolean {
        return tokenSource.getToken() != null
    }
}