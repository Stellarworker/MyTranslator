package com.example.mytranslator.di

import com.example.mytranslator.view.history.HistoryFragmentViewModel
import com.example.mytranslator.view.search.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainKoinModule = module {
    viewModel { MainFragmentViewModel(application = get(), repo = get(), localRepo = get()) }
    viewModel { HistoryFragmentViewModel(localRepo = get()) }
}