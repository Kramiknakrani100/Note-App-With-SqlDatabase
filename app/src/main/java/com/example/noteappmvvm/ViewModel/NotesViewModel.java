package com.example.noteappmvvm.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.noteappmvvm.Model.Notes;
import com.example.noteappmvvm.Repository.NoteRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    NoteRepository repository;
    public LiveData<List<Notes>> getallNotes;
    public LiveData<List<Notes>> hightoLow;
    public LiveData<List<Notes>> lowtoHigh;

    public NotesViewModel(Application application) {
        super(application);

        repository = new NoteRepository(application);
        getallNotes = repository.getallNotes;
        hightoLow = repository.hightoLow;
        lowtoHigh = repository.lowtoHigh;
    }

    public void insertNote(Notes notes){
        repository.insertNotes(notes);
    }

    public void deleteNote(int id){
        repository.deleteNotes(id);
    }

    public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }
}
