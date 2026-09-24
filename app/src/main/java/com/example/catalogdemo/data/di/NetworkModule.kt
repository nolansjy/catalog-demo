package com.example.catalogdemo.data.di

import com.example.catalogdemo.data.network.ApiService
import com.example.catalogdemo.data.repository.RepositoryImpl
import com.example.catalogdemo.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://dummyjson.com"

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providerRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providerApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providerRepositoryImpl(apiService: ApiService) : Repository {
        return RepositoryImpl(apiService)
    }
}