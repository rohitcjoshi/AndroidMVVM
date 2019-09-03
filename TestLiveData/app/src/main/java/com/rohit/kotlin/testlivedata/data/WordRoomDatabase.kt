package com.rohit.kotlin.testlivedata.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1)
public abstract class WordRoomDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "Words_Database"
                ).addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                return INSTANCE!!
            }
        }
    }

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { wordRoomDatabase ->
                scope.launch {
                    Log.d("WordDatabaseCallback", "Adding WordDatabaseCallback----------------->>>>")
                    populateDatabase(wordRoomDatabase.wordDao())
                }
            }
        }

        private fun populateDatabase(dao: WordDao) {
            if(dao.getCount().value!! > 0) {
                Log.d("populateDatabase", "COunter of db is ${dao.getCount().value}")
            } else {
                Log.d("populateDatabase", "Adding dummy data")
                dao.insert(Word(0, "Default 1"))
                dao.insert(Word(0, "Default 2"))
            }
        }


    }

}