package com.example.noteappmvvm.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteappmvvm.Dao.NotesDao
import com.example.noteappmvvm.Model.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao?

    companion object {
        var INSTANCE: NotesDatabase? = null
        @JvmStatic
        fun getDatabaseInstance(context: Context): NotesDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "Notes_Database.db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}