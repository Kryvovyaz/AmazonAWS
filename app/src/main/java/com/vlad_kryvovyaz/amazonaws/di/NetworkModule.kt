package com.vladkryvovyaz.mvvm_hilt_test.di

import com.vlad_kryvovyaz.amazonaws.network.JobsService
import com.vlad_kryvovyaz.amazonaws.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMemeService(retrofit: Retrofit): JobsService =
        retrofit.create(JobsService::class.java)
}