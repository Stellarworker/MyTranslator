package com.example.repository.retrofit.dto

import com.google.gson.annotations.SerializedName

data class DataModelDTO(
    @field:SerializedName(TEXT_SERIALIZED_NAME)
    val text: String? = null,
    @field:SerializedName(MEANINGS_SERIALIZED_NAME)
    val meanings: List<MeaningsDTO>? = null
) {
    companion object {
        private const val TEXT_SERIALIZED_NAME = "text"
        private const val MEANINGS_SERIALIZED_NAME = "meanings"
    }
}
