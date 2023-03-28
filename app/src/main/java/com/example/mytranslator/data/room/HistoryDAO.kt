package com.example.mytranslator.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDAO {

    @Query("SELECT * FROM HistoryEntity")
    fun getAll(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE word = :word")
    fun getRecordsByWord(word: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

}