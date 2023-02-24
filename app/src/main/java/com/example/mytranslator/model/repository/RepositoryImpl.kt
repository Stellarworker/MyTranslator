package com.example.mytranslator.model.repository

import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.retrofit.DictRemoteDataSource
import io.reactivex.Observable

class RepositoryImpl(private val dataSource: DictRemoteDataSource) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}