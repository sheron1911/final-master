package com.example.englishknowledge4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    // 提供對 `WordDao` 的訪問
    abstract fun wordDao(): WordDao

    companion object {
        // 使用 @Volatile 確保 INSTANCE 在多線程間的可見性，避免多個線程創建不同的實例
        @Volatile
        private var INSTANCE: WordDatabase? = null

        // 獲取單例實例的方法
        fun getInstance(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                // 如果 INSTANCE 為 null，則創建數據庫實例
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database" // 數據庫名稱
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}