package com.example.noteappmvvm.Adapter

import com.example.noteappmvvm.MainActivity
import com.example.noteappmvvm.Model.Notes
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappmvvm.Adapter.NotesAdapter.notesViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.noteappmvvm.R
import android.content.Intent
import android.view.View
import com.example.noteappmvvm.Activity.UpdateNoteActivity
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import android.widget.TextView
import java.util.ArrayList

class NotesAdapter(var mainActivity: MainActivity, var notes: List<Notes>) :
    RecyclerView.Adapter<notesViewHolder>() {
    var allNotesItem: List<Notes>
    fun searchnotes(filtername: List<Notes>) {
        notes = filtername
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        val view = LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false)
        return notesViewHolder(view)
    }

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val note = notes[position]
        if (note.notesPriority == "1") {
            holder.notepriority.setBackgroundResource(R.drawable.green_shape)
        }
        if (note.notesPriority == "2") {
            holder.notepriority.setBackgroundResource(R.drawable.yellow_shape)
        }
        if (note.notesPriority == "3") {
            holder.notepriority.setBackgroundResource(R.drawable.red_shape)
        }
        holder.title.text = note.notesTitle
        holder.note.text = note.notes
        holder.notesdate.text = note.notesDate
        holder.itemView.setOnClickListener {
            val intent = Intent(mainActivity, UpdateNoteActivity::class.java)
            intent.putExtra("id", note.id)
            intent.putExtra("title", note.notesTitle)
            intent.putExtra("subtitle", note.notesSubtitle)
            intent.putExtra("notes", note.notes)
            intent.putExtra("priority", note.notesPriority)
            mainActivity.startActivity(intent)
            Animatoo.animateSlideUp(mainActivity)
        }
    }

    fun getNoteAtPosition(position: Int): Notes {
        return notes[position]
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class notesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var note: TextView
        var notesdate: TextView
        var notepriority: View

        init {
            title = itemView.findViewById(R.id.notestitle)
            note = itemView.findViewById(R.id.notessuntitle)
            notesdate = itemView.findViewById(R.id.notesdate)
            notepriority = itemView.findViewById(R.id.notespriority)
        }
    }

    init {
        allNotesItem = ArrayList(notes)
    }
}