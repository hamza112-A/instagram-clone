package com.AppFlix.i220968_i228810.data.api

import com.AppFlix.i220968_i228810.posts.PostApiService
import com.AppFlix.i220968_i228810.stories.StoryApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // Ensure this URL is correct for your setup (Emulator: 10.0.2.2, Physical: 192.168.x.x)
    const val BASE_URL = "http://192.168.18.14/socially_api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Existing APIs
    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
    val messageApi: MessageApiService by lazy { retrofit.create(MessageApiService::class.java) }
    val followApi: FollowApiService by lazy { retrofit.create(FollowApiService::class.java) }
    val presenceApi: PresenceApiService by lazy { retrofit.create(PresenceApiService::class.java) }

    // --- ADD THESE NEW APIs ---
    val postApi: PostApiService by lazy { retrofit.create(PostApiService::class.java) }
    val storyApi: StoryApiService by lazy { retrofit.create(StoryApiService::class.java) }
}