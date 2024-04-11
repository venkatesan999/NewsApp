package com.appsmindstudio.newsinshort.data.api

import com.appsmindstudio.newsinshort.BuildConfig
import com.appsmindstudio.newsinshort.data.AppConstants
import com.appsmindstudio.newsinshort.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(BuildConfig.BASEURL + AppConstants.TOP_HEADLINES)
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = BuildConfig.APIKEY
    ): Response<NewsResponse>
}