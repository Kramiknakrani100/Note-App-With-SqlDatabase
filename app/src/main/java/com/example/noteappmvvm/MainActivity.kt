package com.example.noteappmvvm;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.noteappmvvm.Activity.InsertNoteActivity;
import com.example.noteappmvvm.Adapter.NotesAdapter;
import com.example.noteappmvvm.Model.Notes;
import com.example.noteappmvvm.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotebtn;
    NotesViewModel notesViewModel;
    RecyclerView noterecerview;
    NotesAdapter adapter;
    List<Notes> filternotelist;
    TextView yes, no;
    View deletes;

    TextView nofilter, hightolow, lowtohigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotebtn = findViewById(R.id.newNotesbtn);

        noterecerview = findViewById(R.id.noterecerview);

        nofilter = findViewById(R.id.nofilter);
        hightolow = findViewById(R.id.hightolow);
        lowtohigh = findViewById(R.id.lowtohigh);
        deletes = findViewById(R.id.delete_shhet);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotebtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertNoteActivity.class));
            Animatoo.animateSlideLeft(this);
        });


        notesViewModel.getallNotes.observe(this, notes -> {
            noterecerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            adapter = new NotesAdapter(MainActivity.this, notes);
            noterecerview.setAdapter(adapter);
            filternotelist = notes;
        });

        nofilter.setBackgroundResource(R.drawable.selected_shape);
        nofilter.setOnClickListener(v -> {
            nofilter.setBackgroundResource(R.drawable.selected_shape);
            hightolow.setBackgroundResource(R.drawable.unselected_shape);
            lowtohigh.setBackgroundResource(R.drawable.unselected_shape);

            notesViewModel.getallNotes.observe(MainActivity.this, notes -> {
                noterecerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new NotesAdapter(MainActivity.this, notes);
                noterecerview.setAdapter(adapter);
                filternotelist = notes;
            });
        });

        hightolow.setOnClickListener(v -> {
            nofilter.setBackgroundResource(R.drawable.unselected_shape);
            hightolow.setBackgroundResource(R.drawable.selected_shape);
            lowtohigh.setBackgroundResource(R.drawable.unselected_shape);

            notesViewModel.hightoLow.observe(MainActivity.this, notes -> {
                noterecerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new NotesAdapter(MainActivity.this, notes);
                noterecerview.setAdapter(adapter);
                filternotelist = notes;
            });
        });

        lowtohigh.setOnClickListener(v -> {

            nofilter.setBackgroundResource(R.drawable.unselected_shape);
            hightolow.setBackgroundResource(R.drawable.unselected_shape);
            lowtohigh.setBackgroundResource(R.drawable.selected_shape);

            notesViewModel.lowtoHigh.observe(MainActivity.this, notes -> {
                noterecerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new NotesAdapter(MainActivity.this, notes);
                noterecerview.setAdapter(adapter);
                filternotelist = notes;
            });
        });

        // Add the functionality to swipe items in the
// recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
//                        Word myWord = adapter.getWordAtPosition(position);
//                        Toast.makeText(MainActivity.this, "Deleting " +
//                                myWord.getWord(), Toast.LENGTH_LONG).show();
//
//                        // Delete the word
//                        mWordViewModel.deleteWord(myWord);
                        Notes mynotes = adapter.getNoteAtPosition(position);

                                Toast.makeText(MainActivity.this, "Note Deleted SuccessFully", Toast.LENGTH_SHORT).show();
                                notesViewModel.deleteNote(mynotes.id);}

                });

        helper.attachToRecyclerView(noterecerview);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        android.widget.SearchView searchView = (android.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Notes Here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {

        ArrayList<Notes> Filternames = new ArrayList<>();

        for (Notes notes : this.filternotelist){
            if (notes.notesTitle.toLowerCase().contains(newText.toLowerCase()) ||
                    notes.notesSubtitle.toLowerCase().contains(newText.toLowerCase()) ||
                    notes.notes.toLowerCase().contains(newText.toLowerCase()) ||
                    notes.notesDate.toLowerCase().contains(newText.toLowerCase())){
                Filternames.add(notes);
            }
            this.adapter.searchnotes(Filternames);
        }
    }
}