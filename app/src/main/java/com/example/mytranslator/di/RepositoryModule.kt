package com.example.mytranslator.di

import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.repository.Repository
import com.example.mytranslator.model.repository.RepositoryImpl
import com.example.mytranslator.model.retrofit.DictRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dataSource: DictRemoteDataSource): Repository<List<DataModel>> =
        RepositoryImpl(dataSource)

}