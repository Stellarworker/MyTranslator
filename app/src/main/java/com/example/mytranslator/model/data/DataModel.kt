package com.example.mytranslator.model.data

import com.google.gson.annotations.SerializedName

class DataModel(
    @field:SerializedName("text")
    val text: String? = null,
    @field:SerializedName("meanings")
    val meanings: List<Meanings>? = null
)
