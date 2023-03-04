package com.example.mytranslator.di

import android.app.Application
import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.repository.Repository
import com.example.mytranslator.view.MainActivityViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideViewModel(app: Application, repo: Repository<List<DataModel>>) =
        MainActivityViewModel(app, repo)

}