package com.appsmindstudio.readinnews.data.api

import com.appsmindstudio.readinnews.BuildConfig
import com.appsmindstudio.readinnews.data.AppConstants
import com.appsmindstudio.readinnews.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(BuildConfig.BASEURL + AppConstants.TOP_HEADLINES)
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = BuildConfig.APIKEY
    ): Response<NewsResponse>
}