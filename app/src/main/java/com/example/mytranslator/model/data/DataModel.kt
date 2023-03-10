package com.example.mytranslator.model.data

import com.google.gson.annotations.SerializedName

data class DataModel(
    @field:SerializedName(TEXT_SERIALIZED_NAME)
    val text: String? = null,
    @field:SerializedName(MEANINGS_SERIALIZED_NAME)
    val meanings: List<Meanings>? = null
) {
    companion object {
        private const val TEXT_SERIALIZED_NAME = "text"
        private const val MEANINGS_SERIALIZED_NAME = "meanings"
    }
}
