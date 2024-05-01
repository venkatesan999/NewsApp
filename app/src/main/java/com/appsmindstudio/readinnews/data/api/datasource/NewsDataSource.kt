package com.appsmindstudio.readinnews.data.api.datasource

import com.appsmindstudio.readinnews.data.models.NewsResponse
import retrofit2.Response

interface NewsDataSource {
    suspend fun getNewsHeadline(country: String, category: String): Response<NewsResponse>
}