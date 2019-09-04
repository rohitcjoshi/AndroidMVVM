package com.rohit.kotlin.testlivedata.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 2)
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
                ).addCallback(WordDatabaseCallback(scope)).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return INSTANCE!!
            }
        }
    }

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { wordRoomDatabase ->
                scope.launch (Dispatchers.IO){
                    Log.d("WordDatabaseCallback", "Adding WordDatabaseCallback----------------->>>>")
                    populateDatabase(wordRoomDatabase.wordDao())
                }
            }
        }

        private fun populateDatabase(dao: WordDao) {
            dao.deleteAll()
            Log.d("populateDatabase", "Adding dummy data")
            dao.insert(Word(0, "Hello"))
            dao.insert(Word(0, "Word"))

        }
    }
}