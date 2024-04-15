package com.appsmindstudio.readinnews.data.datasource

import com.appsmindstudio.readinnews.data.entity.NewsResponse
import retrofit2.Response

interface NewsDataSource {

    suspend fun getNewsHeadline(country: String): Response<NewsResponse>
}