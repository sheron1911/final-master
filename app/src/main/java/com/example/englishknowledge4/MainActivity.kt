package com.example.englishknowledge4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.englishknowledge4.data.Word
import com.example.englishknowledge4.data.WordDao
import com.example.englishknowledge4.data.WordDatabase
import com.example.englishknowledge4.screens.AddWordScreen
import com.example.englishknowledge4.screens.VocabularyScreen
import com.example.englishknowledge4.screens.WordDetailScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    lateinit var wordDatabase: WordDatabase
    lateinit var wordDao: WordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wordDatabase = WordDatabase.getInstance(this)
        wordDao = wordDatabase.wordDao()

        setContent {
            val wordList = remember { mutableStateListOf<Word>() }
            LaunchedEffect(Unit) {
                // 使用 coroutine 從資料庫中加載單字
                val wordsFromDb = wordDao.getAllWords()
                wordList.addAll(wordsFromDb)
            }

            MyApp(wordList, onAddWord = {
                wordList.add(it)
                lifecycleScope.launch {
                    wordDao.insertWord(it)
                }

            },  onUpdateWord = { updatedWord ->
                val index = wordList.indexOfFirst { it.id == updatedWord.id }
                if (index >= 0) {
                    wordList[index] = updatedWord  // 更新列表中的單字
                    lifecycleScope.launch {
                        wordDao.updateWord(updatedWord)  // 更新資料庫中的單字
                    }
                }
            },
                onDeleteWord = { wordToDelete ->
                    wordList.removeAll { it.id == wordToDelete.id }  // 從列表中移除
                    lifecycleScope.launch {
                        wordDao.deleteWord(wordToDelete)  // 從資料庫刪除單字
                    }
                })
        }
    }
}

private const val QUIZ_QUESTION_COUNT = 5
private const val QUIZ_OPTION_COUNT = 3

@Composable
fun MyApp(
    wordList: List<Word>,
    onAddWord: (Word) -> Unit,
    onUpdateWord: (Word) -> Unit = {},
    onDeleteWord: (Word) -> Unit = {}
) {
    MaterialTheme(
        typography = Typography(
            displayLarge = TextStyle(fontSize = 24.sp),
            displayMedium = TextStyle(fontSize = 22.sp),
            displaySmall = TextStyle(fontSize = 20.sp),
            headlineLarge = TextStyle(fontSize = 22.sp),
            headlineMedium = TextStyle(fontSize = 20.sp),
            headlineSmall = TextStyle(fontSize = 18.sp),
            bodyLarge = TextStyle(fontSize = 18.sp),
            bodyMedium = TextStyle(fontSize = 16.sp),
            bodySmall = TextStyle(fontSize = 14.sp),
            labelLarge = TextStyle(fontSize = 16.sp),
            labelMedium = TextStyle(fontSize = 14.sp),
            labelSmall = TextStyle(fontSize = 12.sp)
        )
    ) {
        val selectedWord = remember { mutableStateOf<Word?>(null) }
        val isAddWordScreenVisible = remember { mutableStateOf(false) }
        val isQuizScreenVisible = remember { mutableStateOf(false) }
        val quizResult = remember { mutableStateOf<Pair<Int, List<Word>>?>(null) }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isQuizScreenVisible.value -> {
                    QuizScreen(
                        wordList = wordList,
                        onFinishQuiz = { score, incorrectWords ->
                            isQuizScreenVisible.value = false
                            quizResult.value = score to incorrectWords
                        },
                        onBackToMain = { isQuizScreenVisible.value = false }
                    )
                }

                isAddWordScreenVisible.value -> {
                    AddWordScreen(
                        onBackClick = { isAddWordScreenVisible.value = false },
                        onAddWord = { newWord ->
                            onAddWord(newWord)
                            isAddWordScreenVisible.value = false
                        }
                    )
                }

                selectedWord.value != null -> {
                    WordDetailScreen(
                        word = selectedWord.value!!,
                        onBackClick = { selectedWord.value = null }, // 返回主畫面
                        onUpdateWord = { updatedWord ->
                            onUpdateWord(updatedWord)
                            selectedWord.value = null // 更新後回到主畫面
                        },

                        onDeleteWord = { wordToDelete ->
                            onDeleteWord(wordToDelete)
                            selectedWord.value = null // 刪除後回到主畫面
                        }
                    )
                }

                else -> {
                    VocabularyScreen(
                        wordList = wordList,
                        onWordClick = { selectedWord.value = it },
                        onAddWordClick = { isAddWordScreenVisible.value = true }
                    )

                    FloatingActionButton(
                        onClick = { isQuizScreenVisible.value = true },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        Text("開始測驗")
                    }

                    quizResult.value?.let { (score, incorrectWords) ->
                        QuizResultDialog(
                            score = score,
                            incorrectWords = incorrectWords,
                            onRetry = { isQuizScreenVisible.value = true },
                            onBackToMain = { quizResult.value = null }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizScreen(
    wordList: List<Word>,
    onFinishQuiz: (Int, List<Word>) -> Unit,
    onBackToMain: () -> Unit
) {
    if (wordList.size < 4) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("題庫不足") },
            text = { Text("題庫不足，請先新增單字。") },
            confirmButton = {
                Button(onClick = onBackToMain) {
                    Text("返回主畫面")
                }
            },
            dismissButton = {}
        )
        return
    }

    val questions = remember {
        wordList.shuffled().take(QUIZ_QUESTION_COUNT).map { questionWord ->
            val options = (wordList - questionWord).shuffled().distinct().take(QUIZ_OPTION_COUNT - 1) + questionWord
            questionWord to options.shuffled()
        }
    }
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    val incorrectWords = remember { mutableStateListOf<Word>() }

    if (currentIndex < questions.size) {
        val (questionWord, options) = questions[currentIndex]
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("請選擇正確的單字：${questionWord.meaning}")
            Spacer(modifier = Modifier.height(16.dp))
            options.forEach { option ->
                Button(onClick = {
                    if (option == questionWord) score++ else incorrectWords.add(questionWord)
                    currentIndex++
                }) {
                    Text(option.name)
                }
            }
        }
    } else {
        onFinishQuiz(score, incorrectWords)
    }
}

@Composable
fun QuizResultDialog(score: Int, incorrectWords: List<Word>, onRetry: () -> Unit, onBackToMain: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("測驗結果") },
        text = {
            Column {
                Text("你的分數是 $score 分")
                if (incorrectWords.isNotEmpty()) {
                    Text("答錯的題目：")
                    incorrectWords.forEach {
                        Text("${it.meaning} 正確答案是 ${it.name}")
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onRetry) { Text("再試一次") }
        },
        dismissButton = {
            Button(onClick = onBackToMain) { Text("回到主畫面") }
        }
    )
}

@Preview
@Composable
fun SimpleComposablePreview() {
    MyApp(
        wordList = remember {
            mutableStateListOf(
                Word(
                    name = "run",
                    meaning = "跑步",
                    partOfSpeech = "动词",
                    exampleSentence = "She likes to run every morning.",
                    exampleTranslation = "她喜欢每天早上跑步。",
                    note = "常用于表达运动或快速移动的动作。"
                ),
                Word(
                    name = "book",
                    meaning = "书籍",
                    partOfSpeech = "名词",
                    exampleSentence = "He is reading a book.",
                    exampleTranslation = "他正在看一本书。",
                    note = "书籍可以是任何形式的文字或图像的载体。"
                )
            )
        },
        onAddWord = {}
    )
}
