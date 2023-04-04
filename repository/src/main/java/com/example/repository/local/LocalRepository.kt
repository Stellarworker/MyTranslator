package com.example.repository.local

import com.example.core.messages.WordData

interface LocalRepository {
    suspend fun saveRecord(wordData: WordData)
    suspend fun getHistory(): List<WordData>
    suspend fun findWord(word: String): List<WordData>
}