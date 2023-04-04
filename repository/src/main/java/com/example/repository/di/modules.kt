package com.example.repository.di

import androidx.room.Room
import com.example.repository.local.LocalRepository
import com.example.repository.local.LocalRepositoryImpl
import com.example.repository.mappers.DBMapper
import com.example.repository.remote.Repository
import com.example.repository.remote.RepositoryImpl
import com.example.repository.retrofit.DictRemoteDataSource
import com.example.repository.room.HistoryDataBase
import org.koin.dsl.module

val repository = module {
    single { "mytranslator.db" }
    single { DictRemoteDataSource() }
    single<Repository> { RepositoryImpl(dataSource = get()) }
    single { DBMapper() }
    single {
        Room.databaseBuilder(context = get(), klass = HistoryDataBase::class.java, name = get())
            .build()
    }
    single { get<HistoryDataBase>().historyDAO() }
    single<LocalRepository> { LocalRepositoryImpl(dataSource = get(), dbMapper = get()) }
}