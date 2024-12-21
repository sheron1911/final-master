package com.example.englishknowledge4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    // 查詢所有單字，按名稱升序排列
    @Query("SELECT * FROM words ORDER BY name ASC")
    suspend fun getAllWords(): List<Word>

    // 插入單個單字，若主鍵衝突則覆蓋
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    // 插入多個單字，若主鍵衝突則覆蓋
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<Word>)

    // 更新單字
    @Update
    suspend fun updateWord(word: Word)

    // 刪除指定單字
    @Delete
    suspend fun deleteWord(word: Word)

    // 刪除所有單字
    @Query("DELETE FROM words")
    suspend fun deleteAllWords()
}