package com.example.mytranslator.di

import com.example.mytranslator.view.history.HistoryFragmentViewModel
import com.example.mytranslator.view.search.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainKoinModule = module {
    scope(named("GREETING")) {
        scoped { "Hello, user!" }
    }
    viewModel { MainFragmentViewModel(application = get(), repo = get(), localRepo = get()) }
    viewModel { HistoryFragmentViewModel(localRepo = get()) }
}