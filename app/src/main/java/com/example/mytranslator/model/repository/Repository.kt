package com.example.mytranslator.model.repository

import com.example.mytranslator.model.data.DataModel

interface Repository {
    fun getData(word: String, callback: retrofit2.Callback<List<DataModel>>)
}