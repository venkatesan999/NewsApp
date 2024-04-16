package com.appsmindstudio.readinnews.data.api.datasource

import com.appsmindstudio.readinnews.data.api.api_models.NewsResponse
import retrofit2.Response

interface NewsDataSource {
    suspend fun getNewsHeadline(country: String): Response<NewsResponse>
}