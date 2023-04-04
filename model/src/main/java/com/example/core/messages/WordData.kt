package com.example.core.messages

import android.os.Parcelable
import com.example.utils.EMPTY
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordData(
    val word: String = String.EMPTY,
    val translation: String = String.EMPTY,
    val imageUrl: String = String.EMPTY
) : Parcelable