package com.appsmindstudio.readinnews.di

import android.app.Application
import androidx.room.Room
import com.appsmindstudio.readinnews.BuildConfig
import com.appsmindstudio.readinnews.data.api.ApiService
import com.appsmindstudio.readinnews.data.api.datasource.NewsDataSource
import com.appsmindstudio.readinnews.data.api.datasource.NewsDataSourceImpl
import com.appsmindstudio.readinnews.data.repository.NewsRepository
import com.appsmindstudio.readinnews.data.repository.SurveyRepository
import com.appsmindstudio.readinnews.data.room.ReadInNewsDatabase
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
    fun provideApiService(retrofit: Retrofit): ApiService =
        // Defining API endpoints related to weather data
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsDataSource(apiService: ApiService): NewsDataSource =
        NewsDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideNewsRepository(dataSource: NewsDataSource): NewsRepository =
        NewsRepository(dataSource)

    @Provides
    @Singleton
    fun providesDatabase(app: Application): ReadInNewsDatabase =
        Room.databaseBuilder(app, ReadInNewsDatabase::class.java, ReadInNewsDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideSurveyRepository(database: ReadInNewsDatabase): SurveyRepository =
        SurveyRepository(database.surveyDao())

}