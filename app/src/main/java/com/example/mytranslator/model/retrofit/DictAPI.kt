package com.example.mytranslator.model.retrofit

import com.example.mytranslator.model.data.DataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DictAPI {

    @GET(SEARCH_URL_PART)
    fun search(@Query(QUERY) wordToSearch: String): Call<List<DataModel>>

    companion object {
        private const val SEARCH_URL_PART = "words/search"
        private const val QUERY = "search"
    }
}
