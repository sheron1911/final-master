
//主頁面
package com.example.englishknowledge4.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.englishknowledge4.data.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyScreen(
    wordList: List<Word>,
    onWordClick: (Word) -> Unit,
    onAddWordClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "單字學習",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black, // 設置為黑色
                        modifier = Modifier
                            .fillMaxWidth() // 填滿寬度
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center // 置中
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Beige, // 米色背景
                    titleContentColor = Color.Black // 確保文字顏色為黑色
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(wordList) { word ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onWordClick(word) }
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = word.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Row {
                            Text(
                                text = "${word.partOfSpeech}.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = word.meaning,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }

            // 右下角的新增單字按鈕
            FloatingActionButton(
                onClick = onAddWordClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "新增单字")
            }
        }
    }
}

// 定義米色
val Beige = Color(0xFFF5F5DC) // 米色的16進位碼
