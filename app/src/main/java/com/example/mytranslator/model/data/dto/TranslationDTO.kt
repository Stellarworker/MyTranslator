package com.example.mytranslator.model.data.dto

import com.google.gson.annotations.SerializedName

data class TranslationDTO(
    @field:SerializedName(TEXT_SERIALIZED_NAME)
    val text: String? = null,
    @field:SerializedName(NOTE_SERIALIZED_NAME)
    val note: String? = null
) {
    companion object {
        private const val TEXT_SERIALIZED_NAME = "text"
        private const val NOTE_SERIALIZED_NAME = "note"
    }
}
