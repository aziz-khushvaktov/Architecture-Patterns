package com.example.android_mvc.network

import com.example.android_mvc.network.service.PostService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitHttp {
    val IS_TESTER = true
    private val SERVER_DEVELOPMENT = "https://jsonplaceholder.typicode.com/"
    private val SERVER_PRODUCTION = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun server(): String {
        if(IS_TESTER) {
            return SERVER_DEVELOPMENT
        }
        return SERVER_PRODUCTION
    }

    @Provides
    @Singleton
    fun postService(): PostService {
        return retrofitClient().create(PostService::class.java)
    }


}