package com.appsmindstudio.newsinshort.di

import com.appsmindstudio.newsinshort.BuildConfig
import com.appsmindstudio.newsinshort.data.api.ApiService
import com.appsmindstudio.newsinshort.data.datasource.NewsDataSource
import com.appsmindstudio.newsinshort.data.datasource.NewsDataSourceImpl
import com.appsmindstudio.newsinshort.data.repository.NewsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {  // For making network request
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(30, TimeUnit.SECONDS) // 30 seconds for reading from the server
            .callTimeout(30, TimeUnit.SECONDS) // 30 seconds for the overall call
            .connectTimeout(30, TimeUnit.SECONDS) // 30 seconds for establishing a connection
            .writeTimeout(30, TimeUnit.SECONDS) // 30 seconds for writing to the server
            .retryOnConnectionFailure(true)
            .connectionPool(
                ConnectionPool(
                    5,
                    5,
                    TimeUnit.MINUTES
                )
            ) // 5 connections with a 5-minute keep-alive
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {  // Defining API endpoints related to weather data
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDataSource(apiService: ApiService): NewsDataSource {
        return NewsDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(dataSource: NewsDataSource): NewsRepository {
        return NewsRepository(dataSource)
    }
}