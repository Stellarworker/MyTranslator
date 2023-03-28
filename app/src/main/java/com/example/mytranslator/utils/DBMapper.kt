package com.example.mytranslator.utils

import com.example.mytranslator.data.room.HistoryEntity
import com.example.mytranslator.model.data.WordData

class DBMapper {
    private fun map(historyEntity: HistoryEntity) =
        WordData(
            word = historyEntity.word,
            translation = historyEntity.translation,
            imageUrl = historyEntity.imageUrl
        )

    fun map(wordData: WordData) =
        HistoryEntity(
            word = wordData.word,
            translation = wordData.translation,
            imageUrl = wordData.imageUrl
        )

    fun map(historyEntities: List<HistoryEntity>) = historyEntities.map { entity -> map(entity) }

}