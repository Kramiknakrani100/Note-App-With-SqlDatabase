package com.example.noteappmvvm.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.noteappmvvm.Activity.UpdateNoteActivity;
import com.example.noteappmvvm.MainActivity;
import com.example.noteappmvvm.Model.Notes;
import com.example.noteappmvvm.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesItem = new ArrayList<>(notes);
    }

    public void searchnotes(List<Notes> filtername){
        this.notes = filtername;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false);
        return new notesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.notesViewHolder holder, int position) {

        Notes note = notes.get(position);

        if (note.notesPriority.equals("1")) {
            holder.notepriority.setBackgroundResource(R.drawable.green_shape);
        }
        if (note.notesPriority.equals("2")) {
            holder.notepriority.setBackgroundResource(R.drawable.yellow_shape);
        }
        if (note.notesPriority.equals("3")) {
            holder.notepriority.setBackgroundResource(R.drawable.red_shape);
        }

        holder.title.setText(note.notesTitle);
        holder.note.setText(note.notes);
        holder.notesdate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(mainActivity, UpdateNoteActivity.class);

            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle",note.notesSubtitle);
            intent.putExtra("notes",note.notes);
            intent.putExtra("priority",note.notesPriority);

            mainActivity.startActivity(intent);
            Animatoo.animateSlideUp(mainActivity);
        });

    }

    public Notes getNoteAtPosition (int position) {
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class notesViewHolder extends RecyclerView.ViewHolder {

        TextView title, note, notesdate;
        View notepriority;

        public notesViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notestitle);
            note = itemView.findViewById(R.id.notessuntitle);
            notesdate = itemView.findViewById(R.id.notesdate);
            notepriority = itemView.findViewById(R.id.notespriority);
        }
    }
}
