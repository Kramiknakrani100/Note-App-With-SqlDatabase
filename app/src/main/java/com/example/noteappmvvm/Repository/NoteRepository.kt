package com.example.noteappmvvm.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.noteappmvvm.Dao.NotesDao
import com.example.noteappmvvm.Database.NotesDatabase.Companion.getDatabaseInstance
import com.example.noteappmvvm.Model.Notes

class NoteRepository(application: Application?) {
    private var notesDao: NotesDao?
    @JvmField
    var getallNotes: LiveData<List<Notes?>?>?
    @JvmField
    var hightoLow: LiveData<List<Notes?>?>?
    @JvmField
    var lowtoHigh: LiveData<List<Notes?>?>?
    fun insertNotes(notes: Notes?) {
        notesDao!!.insertNotes(notes)
    }

    fun deleteNotes(id: Int) {
        notesDao!!.deleteNotes(id)
    }

    fun updateNotes(notes: Notes?) {
        notesDao!!.updateNotes(notes)
    }

    init {
        val database = getDatabaseInstance(application!!)
        notesDao = database!!.notesDao()
        getallNotes = notesDao!!.getallNotes()
        hightoLow = notesDao!!.hightoLow()
        lowtoHigh = notesDao!!.lowtoHigh()
    }
}