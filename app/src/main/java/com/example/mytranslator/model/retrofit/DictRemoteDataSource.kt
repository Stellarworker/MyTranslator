package com.example.mytranslator.model.retrofit

import com.example.mytranslator.model.data.DataModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictRemoteDataSource {
    private val dictApi = Retrofit
        .Builder()
        .baseUrl(BASE_URL_LOCATIONS)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getData(word: String): Observable<List<DataModel>> =
        dictApi.create(DictAPI::class.java).search(word)

    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}