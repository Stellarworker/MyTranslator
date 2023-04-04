package com.example.repository.remote

import com.example.repository.retrofit.DictRemoteDataSource
import com.example.repository.retrofit.dto.DataModelDTO
import retrofit2.Callback

class RepositoryImpl(private val dataSource: DictRemoteDataSource) :
    Repository {

    override fun getData(word: String, callback: Callback<List<DataModelDTO>>) {
        dataSource.getData(word, callback)
    }
}