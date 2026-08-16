package com.AppFlix.i220968_i228810.stories

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StoryNetworkModule {
    private const val BASE_URL = "http://192.168.18.14/socially_api/" // TODO: change

    private val client = OkHttpClient.Builder().build()

    val api: StoryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StoryApiService::class.java)
    }
}
