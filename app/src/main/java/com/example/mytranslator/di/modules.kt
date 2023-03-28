package com.example.mytranslator.di

import com.example.mytranslator.App
import com.example.mytranslator.model.repository.local.LocalRepository
import com.example.mytranslator.model.repository.local.LocalRepositoryImpl
import com.example.mytranslator.model.repository.remote.Repository
import com.example.mytranslator.model.repository.remote.RepositoryImpl
import com.example.mytranslator.model.retrofit.DictRemoteDataSource
import com.example.mytranslator.utils.DBMapper
import com.example.mytranslator.view.history.HistoryFragmentViewModel
import com.example.mytranslator.view.search.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainKoinModule = module {
    single { DictRemoteDataSource() }
    single<Repository> { RepositoryImpl(dataSource = get()) }
    single { DBMapper() }
    single { App.getHistoryDAO() }
    single<LocalRepository> { LocalRepositoryImpl(dataSource = get(), dbMapper = get()) }
    viewModel { MainFragmentViewModel(application = get(), repo = get(), localRepo = get()) }
    viewModel { HistoryFragmentViewModel(localRepo = get()) }
}