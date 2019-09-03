package com.rohit.kotlin.testlivedata.data

import androidx.annotation.Size
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word): Long

    @Query("select * from words_table")
    fun getAllWords(): LiveData<List<Word>>

    @Query("delete from words_table")
    fun deleteAll()

    @Query("select count(id) from words_table")
    fun getCount(): LiveData<Int>
}