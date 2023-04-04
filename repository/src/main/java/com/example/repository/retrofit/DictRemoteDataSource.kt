package com.example.repository.retrofit

import com.example.repository.retrofit.dto.DataModelDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictRemoteDataSource {
    private val dictApi = Retrofit
        .Builder()
        .baseUrl(BASE_URL_LOCATIONS)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(DictAPI::class.java)

    fun getData(word: String, callback: Callback<List<DataModelDTO>>) {
        dictApi.search(word).enqueue(callback)
    }

    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}