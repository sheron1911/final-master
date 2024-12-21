package com.example.englishknowledge4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 唯一ID，自動生成
    val name: String, // 單字本身
    val meaning: String, // 單字的意思
    val partOfSpeech: String, // 詞性，例如名詞、動詞
    val exampleSentence: String, // 使用例句
    val exampleTranslation: String, // 例句的翻譯
    val note: String // 附加的註解
)
