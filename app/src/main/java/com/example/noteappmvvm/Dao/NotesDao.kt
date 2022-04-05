package com.example.noteappmvvm.Dao

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.example.noteappmvvm.Model.Notes
import androidx.room.Update

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes_Database")
    fun getallNotes(): LiveData<List<Notes?>?>?

    @Query("SELECT * FROM Notes_Database ORDER BY notes_priority DESC")
    fun hightoLow(): LiveData<List<Notes?>?>?

    @Query("SELECT * FROM Notes_Database ORDER BY notes_priority ASC")
    fun lowtoHigh(): LiveData<List<Notes?>?>?

    @Insert
    fun insertNotes(vararg notes: Notes?)

    @Query("DELETE FROM Notes_Database WHERE id=:id")
    fun deleteNotes(id: Int)

    @Update
    fun updateNotes(notes: Notes?)
}