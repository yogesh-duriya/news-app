package com.example.newsapplication.di

import com.example.newsapplication.data.repository.NewsRepository
import com.example.newsapplication.data.repository.impl.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(userRepositoryImpl: NewsRepositoryImpl): NewsRepository =
        userRepositoryImpl

}