package com.appsmindstudio.readinnews.data.api

import com.appsmindstudio.readinnews.BuildConfig
import com.appsmindstudio.readinnews.data.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(BuildConfig.BASEURL + "top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("category") business: String,
        @Query("apiKey") apiKey: String = BuildConfig.APIKEY
    ): Response<NewsResponse>
}