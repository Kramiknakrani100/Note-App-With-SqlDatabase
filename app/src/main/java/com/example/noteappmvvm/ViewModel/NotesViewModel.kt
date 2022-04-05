package com.example.noteappmvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteappmvvm.Model.Notes
import com.example.noteappmvvm.Repository.NoteRepository

class NotesViewModel(application: Application?) : AndroidViewModel(
    application!!
) {
    private var repository: NoteRepository = NoteRepository(application)
    var getallNotes: LiveData<List<Notes?>?>? = repository.getallNotes
    var hightoLow: LiveData<List<Notes?>?>? = repository.hightoLow
    var lowtoHigh: LiveData<List<Notes?>?>? = repository.lowtoHigh
    fun insertNote(notes: Notes?) {
        repository.insertNotes(notes)
    }

    fun deleteNote(id: Int) {
        repository.deleteNotes(id)
    }

    fun updateNote(notes: Notes?) {
        repository.updateNotes(notes)
    }

}