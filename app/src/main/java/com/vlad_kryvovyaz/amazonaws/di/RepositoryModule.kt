package com.vladkryvovyaz.mvvm_hilt_test.di

import com.vlad_kryvovyaz.amazonaws.network.JobsService
import com.vlad_kryvovyaz.amazonaws.repository.JobsContainerRepository
import com.vlad_kryvovyaz.amazonaws.repository.JobsContainerRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideJobsRepository(memeService: JobsService): JobsContainerRepositoryImp {
        return JobsContainerRepository(memeService)
    }
}