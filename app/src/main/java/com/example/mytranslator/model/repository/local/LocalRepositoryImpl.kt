package com.example.mytranslator.model.repository.local

import com.example.mytranslator.data.room.HistoryDAO
import com.example.mytranslator.model.data.WordData
import com.example.mytranslator.utils.DBMapper

class LocalRepositoryImpl(
    private val dataSource: HistoryDAO,
    private val dbMapper: DBMapper
) : LocalRepository {
    override fun saveRecord(wordData: WordData) {
        dataSource.insert(dbMapper.map(wordData))
    }

    override fun getHistory() = dbMapper.map(dataSource.getAll())

    override fun findWord(word: String) = dbMapper.map(dataSource.getRecordsByWord(word))
}