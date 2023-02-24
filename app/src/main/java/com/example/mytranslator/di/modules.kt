package com.example.mytranslator.di

import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.repository.Repository
import com.example.mytranslator.model.repository.RepositoryImpl
import com.example.mytranslator.model.retrofit.DictRemoteDataSource
import com.example.mytranslator.presenter.MainActivityPresenterContract
import com.example.mytranslator.view.MainActivityPresenter
import org.koin.dsl.module

val mainKoinModule = module {
    single { DictRemoteDataSource() }
    single<Repository<List<DataModel>>> { RepositoryImpl(get()) }
    single<MainActivityPresenterContract> { MainActivityPresenter(get()) }
}