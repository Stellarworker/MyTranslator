package com.example.mytranslator.model.data

import android.os.Parcelable
import com.example.mytranslator.utils.EMPTY
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordData(
    val word: String = String.EMPTY,
    val translation: String = String.EMPTY,
    val imageUrl: String = String.EMPTY
) : Parcelable