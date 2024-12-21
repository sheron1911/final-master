//單字詳細頁面
package com.example.englishknowledge4.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.englishknowledge4.data.Word


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordDetailScreen(
    word: Word,
    onBackClick: () -> Unit,
    onUpdateWord: (Word) -> Unit,
    onDeleteWord: (Word) -> Unit

) {
    var name by remember { mutableStateOf(word.name) }
    var meaning by remember { mutableStateOf(word.meaning) }
    var partOfSpeech by remember { mutableStateOf(word.partOfSpeech) }
    var exampleSentence by remember { mutableStateOf(word.exampleSentence) }
    var exampleTranslation by remember { mutableStateOf(word.exampleTranslation) }
    var note by remember { mutableStateOf(word.note) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "單字詳情",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.Black,
                            modifier = Modifier.offset(x = (-16).dp) // 左移16dp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "返回",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F5DC)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp) // 整個畫面的內邊距
        ) {
            // 單字名稱輸入框
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("單字") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // 移除外框顏色
                    focusedIndicatorColor = Color.Transparent, // 移除選中下劃線
                    unfocusedIndicatorColor = Color.Transparent // 移除未選中下劃線
                )
            )

            // 中文意思輸入框
            TextField(
                value = meaning,
                onValueChange = { meaning = it },
                label = { Text("意思") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // 詞性輸入框
            TextField(
                value = partOfSpeech,
                onValueChange = { partOfSpeech = it },
                label = { Text("詞性") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // 例句輸入框
            TextField(
                value = exampleSentence,
                onValueChange = { exampleSentence = it },
                label = { Text("例句") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // 例句翻譯輸入框
            TextField(
                value = exampleTranslation,
                onValueChange = { exampleTranslation = it },
                label = { Text("例句翻譯") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // 註解輸入框
            TextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("註解") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // 使用 Spacer 推動按鈕到屏幕底部
            Spacer(modifier = Modifier.weight(1f))

            // 修改與刪除按鈕
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp), // 兩個按鈕之間的間距
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onUpdateWord(
                            Word(
                                name = name,
                                meaning = meaning,
                                partOfSpeech = partOfSpeech,
                                exampleSentence = exampleSentence,
                                exampleTranslation = exampleTranslation,
                                note = note
                            )
                        )
                    },
                    modifier = Modifier.weight(1f) // 按鈕均分寬度
                ) {
                    Text("修改")
                }
                Button(
                    onClick ={
                        onDeleteWord(word)
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f) // 按鈕均分寬度
                ) {
                    Text("刪除")
                }
            }
        }
    }
}
