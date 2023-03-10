package com.example.mytranslator.model.data

import com.google.gson.annotations.SerializedName

data class Translation(
    @field:SerializedName(TEXT_SERIALIZED_NAME)
    val translation: String? = null
) {
    companion object {
        private const val TEXT_SERIALIZED_NAME = "text"
    }
}
