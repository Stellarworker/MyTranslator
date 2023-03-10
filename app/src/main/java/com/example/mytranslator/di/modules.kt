package com.example.mytranslator.di

import com.example.mytranslator.model.repository.Repository
import com.example.mytranslator.model.repository.RepositoryImpl
import com.example.mytranslator.model.retrofit.DictRemoteDataSource
import com.example.mytranslator.view.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainKoinModule = module {
    single { DictRemoteDataSource() }
    single<Repository> { RepositoryImpl(dataSource = get()) }
    viewModel { MainActivityViewModel(application = get(), repo = get()) }
}