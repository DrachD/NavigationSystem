package com.dmitriy.instagramclone.sources

import com.dmitriy.instagramclone.app.Constants
import com.dmitriy.instagramclone.app.Singleton
import com.dmitriy.instagramclone.app.model.accounts.UserSource
import com.dmitriy.instagramclone.app.model.peoples.PeopleSource
import com.dmitriy.instagramclone.app.model.settings.TokenSource
import com.dmitriy.instagramclone.sources.peoples.PeopleApi
import com.dmitriy.instagramclone.sources.accounts.UserApi
import com.dmitriy.instagramclone.sources.accounts.entities.SignInRequest
import com.dmitriy.instagramclone.sources.accounts.entities.SignUpRequest
import com.dmitriy.instagramclone.sources.peoples.entities.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SourceProviderHolder : UserSource, PeopleSource {

    private val retrofitUser: UserApi = createRetrofit().create(UserApi::class.java)
    private val retrofitPeople: PeopleApi = createRetrofit().create(PeopleApi::class.java)

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Create an instance of Retrofit client
     */
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor(Singleton.sharedPreferencesToken))
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createAuthorizationInterceptor(tokenSource: TokenSource): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            val token = tokenSource.getToken()
            if (token != null) {
                newBuilder.addHeader("Authorization", token)
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // ----- UserApi -----

    override suspend fun signUp(user: SignUpRequest) = retrofitUser.signUp(user)
    override suspend fun signIn(user: SignInRequest) = retrofitUser.signIn(user)

    // ----- PeopleApi -----

    override suspend fun getListUsers(page: Int) = retrofitPeople.getListUsers(page)
    override suspend fun getSingleUser(userId: Int) = retrofitPeople.getSingleUser(userId)
    override suspend fun addUser(createUserRequest: CreateUserRequest) = retrofitPeople.addUser(createUserRequest)
    override suspend fun updateUser(userId: Int, updateUserResponse: UpdateUserRequest) = retrofitPeople.updateUser(userId, updateUserResponse)
    override suspend fun deleteUser(userId: Int) = retrofitPeople.deleteUser(userId)
}