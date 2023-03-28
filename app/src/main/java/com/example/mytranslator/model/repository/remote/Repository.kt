package com.example.mytranslator.model.repository.remote

import com.example.mytranslator.model.data.dto.DataModelDTO

interface Repository {
    fun getData(word: String, callback: retrofit2.Callback<List<DataModelDTO>>)
}