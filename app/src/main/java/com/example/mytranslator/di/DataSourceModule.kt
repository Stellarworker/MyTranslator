package com.example.mytranslator.di

import com.example.mytranslator.model.retrofit.DictRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideDataSource(): DictRemoteDataSource = DictRemoteDataSource()
}