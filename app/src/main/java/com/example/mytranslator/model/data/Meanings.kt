package com.example.mytranslator.model.data

import com.google.gson.annotations.SerializedName

data class Meanings(
    @field:SerializedName(TRANSLATION_SERIALIZED_NAME)
    val translation: Translation? = null
) {
    companion object {
        private const val TRANSLATION_SERIALIZED_NAME = "translation"
    }
}
