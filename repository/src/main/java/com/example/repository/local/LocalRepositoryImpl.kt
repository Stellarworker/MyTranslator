package com.example.repository.local

import com.example.core.messages.WordData
import com.example.repository.room.HistoryDAO
import com.example.repository.mappers.DBMapper

class LocalRepositoryImpl(
    private val dataSource: HistoryDAO,
    private val dbMapper: DBMapper
) : LocalRepository {
    override suspend fun saveRecord(wordData: WordData) {
        dataSource.insert(dbMapper.map(wordData))
    }

    override suspend fun getHistory() = dbMapper.map(dataSource.getAll())

    override suspend fun findWord(word: String) = dbMapper.map(dataSource.getRecordsByWord(word))
}