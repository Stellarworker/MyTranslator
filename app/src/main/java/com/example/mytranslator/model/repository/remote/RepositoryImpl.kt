package com.example.mytranslator.model.repository.remote

import com.example.mytranslator.model.data.dto.DataModelDTO
import com.example.mytranslator.model.retrofit.DictRemoteDataSource

class RepositoryImpl(private val dataSource: DictRemoteDataSource) :
    Repository {

    override fun getData(word: String, callback: retrofit2.Callback<List<DataModelDTO>>) {
        dataSource.getData(word, callback)
    }
}