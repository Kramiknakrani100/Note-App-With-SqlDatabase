package com.example.noteappmvvm.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.noteappmvvm.Dao.NotesDao;
import com.example.noteappmvvm.Database.NotesDatabase;
import com.example.noteappmvvm.Model.Notes;

import java.util.List;

public class NoteRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getallNotes;
    public LiveData<List<Notes>> hightoLow;
    public LiveData<List<Notes>> lowtoHigh;


    public  NoteRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getallNotes = notesDao.getallNotes();
        hightoLow = notesDao.hightoLow();
        lowtoHigh = notesDao.lowtoHigh();

    }

    public void insertNotes(Notes notes) {
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id) {
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes) {
        notesDao.updateNotes(notes);
    }
}
