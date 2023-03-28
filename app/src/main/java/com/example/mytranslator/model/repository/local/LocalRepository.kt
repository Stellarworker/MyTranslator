package com.example.mytranslator.model.repository.local

import com.example.mytranslator.model.data.WordData

interface LocalRepository {
    fun saveRecord(wordData: WordData)
    fun getHistory(): List<WordData>
    fun findWord(word: String): List<WordData>
}