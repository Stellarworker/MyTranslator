package com.example.mytranslator.model.data.dto

import com.google.gson.annotations.SerializedName

data class MeaningsDTO(
    @field:SerializedName(TRANSLATION_SERIALIZED_NAME)
    val translationDTO: TranslationDTO? = null,
    @field:SerializedName(IMAGE_URL_SERIALIZED_NAME)
    val imageUrl: String? = null,

    ) {
    companion object {
        private const val TRANSLATION_SERIALIZED_NAME = "translation"
        private const val IMAGE_URL_SERIALIZED_NAME = "imageUrl"
    }
}
