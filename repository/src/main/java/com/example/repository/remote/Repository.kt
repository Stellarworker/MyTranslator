package com.example.repository.remote

import com.example.repository.retrofit.dto.DataModelDTO

interface Repository {
    fun getData(word: String, callback: retrofit2.Callback<List<DataModelDTO>>)
}