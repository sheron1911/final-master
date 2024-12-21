//新增頁面

package com.example.englishknowledge4.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.englishknowledge4.data.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(
    onBackClick: () -> Unit,
    onAddWord: (Word) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "新增單字",
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
                .padding(16.dp) // 畫面內容的內邊距
        ) {
            // 單字名稱輸入框
            var name by remember { mutableStateOf("") }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("單字") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // 與下一個輸入框間距
            )

            // 單字意思輸入框
            var meaning by remember { mutableStateOf("") }
            OutlinedTextField(
                value = meaning,
                onValueChange = { meaning = it },
                label = { Text("意思") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // 詞性輸入框
            var partOfSpeech by remember { mutableStateOf("") }
            OutlinedTextField(
                value = partOfSpeech,
                onValueChange = { partOfSpeech = it },
                label = { Text("詞性") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // 例句輸入框
            var exampleSentence by remember { mutableStateOf("") }
            OutlinedTextField(
                value = exampleSentence,
                onValueChange = { exampleSentence = it },
                label = { Text("例句") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // 例句翻譯輸入框
            var exampleTranslation by remember { mutableStateOf("") }
            OutlinedTextField(
                value = exampleTranslation,
                onValueChange = { exampleTranslation = it },
                label = { Text("例句中文翻譯") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // 註解輸入框
            var note by remember { mutableStateOf("") }
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("註解") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // 新增按鈕
            Button(
                onClick = {
                    if (name.isNotEmpty() && meaning.isNotEmpty() && partOfSpeech.isNotEmpty()) {
                        onAddWord(
                            Word(
                                name = name,
                                meaning = meaning,
                                partOfSpeech = partOfSpeech,
                                exampleSentence = exampleSentence,
                                exampleTranslation = exampleTranslation,
                                note = note
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("新增")
            }
        }
    }
}
