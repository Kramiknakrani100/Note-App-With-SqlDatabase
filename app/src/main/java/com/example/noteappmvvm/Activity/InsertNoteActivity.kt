package com.example.noteappmvvm.Activity

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.noteappmvvm.Model.Notes
import com.example.noteappmvvm.R
import com.example.noteappmvvm.ViewModel.NotesViewModel
import com.example.noteappmvvm.databinding.ActivityInsertNoteBinding
import java.util.*

class InsertNoteActivity : AppCompatActivity() {
    private var binding: ActivityInsertNoteBinding? = null
    var title: String? = null
    var subtitle: String? = null
    var notes: String? = null
    private var notesViewModel: NotesViewModel? = null
    var priority = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertNoteBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        binding!!.greenpriority.setOnClickListener { v: View? ->
            binding!!.greenpriority.setImageResource(R.drawable.ic_done)
            binding!!.yellowpriority.setImageResource(0)
            binding!!.redpriority.setImageResource(0)
            priority = "1"
        }
        binding!!.yellowpriority.setOnClickListener { v: View? ->
            binding!!.greenpriority.setImageResource(0)
            binding!!.yellowpriority.setImageResource(R.drawable.ic_done)
            binding!!.redpriority.setImageResource(0)
            priority = "2"
        }
        binding!!.redpriority.setOnClickListener { v: View? ->
            binding!!.greenpriority.setImageResource(0)
            binding!!.yellowpriority.setImageResource(0)
            binding!!.redpriority.setImageResource(R.drawable.ic_done)
            priority = "3"
        }
        binding!!.doneNotesbtn.setOnClickListener { v: View? ->
            title = binding!!.notesTitle.text.toString()
            subtitle = binding!!.notesSubTitle.text.toString()
            notes = binding!!.notesData.text.toString()
            CreateNotes(title!!, subtitle!!, notes!!)
            Animatoo.animateSlideRight(this)
        }
    }

    private fun CreateNotes(title: String, subtitle: String, notes: String) {
        val date = Date()
        val sequence = DateFormat.format("MMM d, yyyy", date.time)
        val notes1 = Notes()
        notes1.notesTitle = title
        notes1.notesSubtitle = subtitle
        notes1.notes = notes
        notes1.notesPriority = priority
        notes1.notesDate = sequence.toString()
        notesViewModel!!.insertNote(notes1)
        Toast.makeText(this, "Note Create SuccessFully", Toast.LENGTH_SHORT).show()
        //Log.d("kramik","Note Create SuccessFully");
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        title = binding!!.notesTitle.text.toString()
        subtitle = binding!!.notesSubTitle.text.toString()
        notes = binding!!.notesData.text.toString()
        CreateNotes(title!!, subtitle!!, notes!!)
        Animatoo.animateSlideRight(this)
    }
}