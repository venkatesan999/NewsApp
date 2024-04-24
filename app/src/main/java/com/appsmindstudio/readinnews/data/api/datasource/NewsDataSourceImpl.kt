package com.appsmindstudio.readinnews.data.api.datasource

import com.appsmindstudio.readinnews.data.api.ApiService
import com.appsmindstudio.readinnews.data.api.api_models.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(private val apiService: ApiService) : NewsDataSource {
    override suspend fun getNewsHeadline(country: String, category: String): Response<NewsResponse> {
        return apiService.getNewsHeadline(country, category)
    }
}