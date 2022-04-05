package com.example.noteappmvvm.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.noteappmvvm.Model.Notes
import com.example.noteappmvvm.R
import com.example.noteappmvvm.ViewModel.NotesViewModel
import com.example.noteappmvvm.databinding.ActivityUpdateNoteBinding
import java.util.*

class UpdateNoteActivity : AppCompatActivity() {
    private var binding: ActivityUpdateNoteBinding? = null
    private var priority = "1"
    private var sTitle: String? = null
    private var sSubTitle: String? = null
    private var sNotes: String? = null
    private var sPriority: String? = null
    private var sId = 0
    private var notesViewModel: NotesViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        notesViewModel = ViewModelProviders.of(this)[NotesViewModel::class.java]
        sTitle = intent.getStringExtra("title")
        sId = intent.getIntExtra("id", 0)
        sSubTitle = intent.getStringExtra("subtitle")
        sNotes = intent.getStringExtra("notes")
        sPriority = intent.getStringExtra("priority")
        binding!!.updateTitle.setText(sTitle)
        binding!!.updatesunTitle.setText(sSubTitle)
        binding!!.updateNotes.setText(sNotes)
        if (sPriority == "1") {
            binding!!.greenpriority.setImageResource(R.drawable.ic_done)
        }
        if (sPriority == "2") {
            binding!!.yellowpriority.setImageResource(R.drawable.ic_done)
        }
        if (sPriority == "3") {
            binding!!.redpriority.setImageResource(R.drawable.ic_done)
        }
        binding!!.greenpriority.setOnClickListener {
            binding!!.greenpriority.setImageResource(R.drawable.ic_done)
            binding!!.yellowpriority.setImageResource(0)
            binding!!.redpriority.setImageResource(0)
            priority = "1"
        }
        binding!!.yellowpriority.setOnClickListener {
            binding!!.greenpriority.setImageResource(0)
            binding!!.yellowpriority.setImageResource(R.drawable.ic_done)
            binding!!.redpriority.setImageResource(0)
            priority = "2"
        }
        binding!!.redpriority.setOnClickListener {
            binding!!.greenpriority.setImageResource(0)
            binding!!.yellowpriority.setImageResource(0)
            binding!!.redpriority.setImageResource(R.drawable.ic_done)
            priority = "3"
        }
        binding!!.updateNotesbtn.setOnClickListener {
            val title = binding!!.updateTitle.text.toString()
            val subtitle = binding!!.updatesunTitle.text.toString()
            val notes = binding!!.updateNotes.text.toString()
            UpdateNotes(title, subtitle, notes)
            Animatoo.animateSlideDown(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.update_menu, menu)
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.share) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Title:- $sTitle\nSubtitle:- $sSubTitle\nNote:- $sNotes"
            )
            intent.type = "jpg/plain"
            intent.type = "text/plain"
            val chooser = Intent.createChooser(intent, "Share This Note...")
            startActivity(chooser)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun UpdateNotes(title: String?, subtitle: String?, notes: String?) {
        val date = Date()
        val sequence = DateFormat.format("MMM d, yyyy", date.time)
        val updateNotes = Notes()
        updateNotes.id = sId
        updateNotes.notesTitle = title
        updateNotes.notesSubtitle = subtitle
        updateNotes.notes = notes
        updateNotes.notesPriority = priority
        updateNotes.notesDate = sequence.toString()
        notesViewModel!!.updateNote(updateNotes)
        Toast.makeText(this@UpdateNoteActivity, "Note Updated SuccessFully", Toast.LENGTH_SHORT)
            .show()
        //Log.d("kramik","Note Create SuccessFully");
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val title = binding!!.updateTitle.text.toString()
        val subtitle = binding!!.updatesunTitle.text.toString()
        val notes = binding!!.updateNotes.text.toString()
        UpdateNotes(title, subtitle, notes)
        Animatoo.animateSlideDown(this)
    }
}