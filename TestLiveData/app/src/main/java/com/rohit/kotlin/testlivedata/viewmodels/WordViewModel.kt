package com.rohit.kotlin.testlivedata.viewmodels

import android.app.Application
import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rohit.kotlin.testlivedata.data.Word
import com.rohit.kotlin.testlivedata.data.WordRoomDatabase
import com.rohit.kotlin.testlivedata.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application) {
    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {
        val wordDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    @WorkerThread
    fun insertWord(word: Word) = viewModelScope.launch(Dispatchers.IO){
        repository.insertWord(word)
    }
}