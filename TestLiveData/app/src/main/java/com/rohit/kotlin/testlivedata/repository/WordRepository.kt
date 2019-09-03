package com.rohit.kotlin.testlivedata.repository

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.rohit.kotlin.testlivedata.data.Word
import com.rohit.kotlin.testlivedata.data.WordDao

class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()


    suspend fun insertWord(word: Word) {
        wordDao.insert(word)
    }
}