package com.example.mytranslator.model.repository

import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.retrofit.DictRemoteDataSource

class RepositoryImpl(private val dataSource: DictRemoteDataSource) :
    Repository {

    override fun getData(word: String, callback: retrofit2.Callback<List<DataModel>>) {
        dataSource.getData(word, callback)
    }
}