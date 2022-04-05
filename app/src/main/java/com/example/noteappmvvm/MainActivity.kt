package com.example.noteappmvvm

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.noteappmvvm.Activity.InsertNoteActivity
import com.example.noteappmvvm.Adapter.NotesAdapter
import com.example.noteappmvvm.Model.Notes
import com.example.noteappmvvm.ViewModel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var newNotebtn: FloatingActionButton? = null
    var notesViewModel: NotesViewModel? = null
    var noterecerview: RecyclerView? = null
    var adapter: NotesAdapter? = null
    var filternotelist: List<Notes>? = null
    var yes: TextView? = null
    var no: TextView? = null
    var deletes: View? = null
    var nofilter: TextView? = null
    var hightolow: TextView? = null
    var lowtohigh: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newNotebtn = findViewById(R.id.newNotesbtn)
        noterecerview = findViewById(R.id.noterecerview)
        nofilter = findViewById(R.id.nofilter)
        hightolow = findViewById(R.id.hightolow)
        lowtohigh = findViewById(R.id.lowtohigh)
        deletes = findViewById(R.id.delete_shhet)
        yes = findViewById(R.id.yes)
        no = findViewById(R.id.no)
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        newNotebtn?.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(Intent(this@MainActivity, InsertNoteActivity::class.java))
            Animatoo.animateSlideLeft(this)
        })
        notesViewModel!!.getallNotes?.observe(this) { notes: List<Notes>? ->
            noterecerview?.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = NotesAdapter(this@MainActivity, notes!!)
            noterecerview?.setAdapter(adapter)
            filternotelist = notes
        } as (List<Notes?>?) -> Unit as (List<Notes?>?) -> Unit
        nofilter?.setBackgroundResource(R.drawable.selected_shape)
        nofilter?.setOnClickListener(View.OnClickListener { v: View? ->
            nofilter?.setBackgroundResource(R.drawable.selected_shape)
            hightolow?.setBackgroundResource(R.drawable.unselected_shape)
            lowtohigh?.setBackgroundResource(R.drawable.unselected_shape)
            notesViewModel!!.getallNotes?.observe(this@MainActivity) { notes: List<Notes>? ->
                noterecerview?.setLayoutManager(
                    StaggeredGridLayoutManager(
                        2,
                        StaggeredGridLayoutManager.VERTICAL
                    )
                )
                adapter = NotesAdapter(this@MainActivity, notes!!)
                noterecerview?.setAdapter(adapter)
                filternotelist = notes
            } as (List<Notes?>?) -> Unit as (List<Notes?>?) -> Unit as (List<Notes?>?) -> Unit
        })
        hightolow?.setOnClickListener(View.OnClickListener { v: View? ->
            nofilter?.setBackgroundResource(R.drawable.unselected_shape)
            hightolow?.setBackgroundResource(R.drawable.selected_shape)
            lowtohigh?.setBackgroundResource(R.drawable.unselected_shape)
            notesViewModel!!.hightoLow?.observe(this@MainActivity) { notes: List<Notes>? ->
                noterecerview?.setLayoutManager(
                    StaggeredGridLayoutManager(
                        2,
                        StaggeredGridLayoutManager.VERTICAL
                    )
                )
                adapter = NotesAdapter(this@MainActivity, notes!!)
                noterecerview?.setAdapter(adapter)
                filternotelist = notes
            } as (List<Notes?>?) -> Unit as (List<Notes?>?) -> Unit as (List<Notes?>?) -> Unit
        })
        lowtohigh?.setOnClickListener(View.OnClickListener { v: View? ->
            nofilter?.setBackgroundResource(R.drawable.unselected_shape)
            hightolow?.setBackgroundResource(R.drawable.unselected_shape)
            lowtohigh?.setBackgroundResource(R.drawable.selected_shape)
            notesViewModel!!.lowtoHigh?.observe(this@MainActivity) { notes: List<Notes>? ->
                noterecerview?.setLayoutManager(
                    StaggeredGridLayoutManager(
                        2,
                        StaggeredGridLayoutManager.VERTICAL
                    )
                )
                adapter = NotesAdapter(this@MainActivity, notes!!)
                noterecerview?.setAdapter(adapter)
                filternotelist = notes
            } as (List<Notes?>?) -> Unit as (List<Notes?>?) -> Unit as (List<Notes?>?) -> Unit
        })
        // Add the functionality to swipe items in the
// recycler view to delete that item
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.adapterPosition
                    //                        Word myWord = adapter.getWordAtPosition(position);
//                        Toast.makeText(MainActivity.this, "Deleting " +
//                                myWord.getWord(), Toast.LENGTH_LONG).show();
//
//                        // Delete the word
//                        mWordViewModel.deleteWord(myWord);
                    val mynotes = adapter!!.getNoteAtPosition(position)
                    Toast.makeText(
                        this@MainActivity,
                        "Note Deleted SuccessFully",
                        Toast.LENGTH_SHORT
                    ).show()
                    notesViewModel!!.deleteNote(mynotes.id)
                }
            })
        helper.attachToRecyclerView(noterecerview)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search Notes Here..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                NotesFilter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun NotesFilter(newText: String) {
        val Filternames = ArrayList<Notes>()
        for (notes in filternotelist!!) {
            if (notes.notesTitle?.lowercase(Locale.getDefault())?.contains(newText.lowercase(Locale.getDefault())) == true ||
                notes.notesSubtitle?.lowercase(Locale.getDefault())?.contains(newText.lowercase(
                    Locale.getDefault()
                )) == true ||
                notes.notes?.lowercase(Locale.getDefault())?.contains(newText.lowercase(Locale.getDefault())) ==  true ||
                    notes.notesDate?.lowercase(Locale.getDefault())?.contains(newText.lowercase(Locale.getDefault())) == true
            ) {
                Filternames.add(notes)
            }
            adapter!!.searchnotes(Filternames)
        }
    }
}