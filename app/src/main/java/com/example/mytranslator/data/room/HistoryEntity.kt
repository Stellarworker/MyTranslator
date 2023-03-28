package com.example.mytranslator.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mytranslator.utils.EMPTY
import com.example.mytranslator.utils.ZERO

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = Long.ZERO,

    @field:ColumnInfo(name = "word")
    val word: String = String.EMPTY,

    @field:ColumnInfo(name = "translation")
    val translation: String = String.EMPTY,

    @field:ColumnInfo(name = "imageUrl")
    val imageUrl: String = String.EMPTY
)
