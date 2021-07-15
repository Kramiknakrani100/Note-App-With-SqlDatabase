package com.example.noteappmvvm.Activity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.noteappmvvm.Model.Notes;
import com.example.noteappmvvm.R;
import com.example.noteappmvvm.ViewModel.NotesViewModel;
import com.example.noteappmvvm.databinding.ActivityInsertNoteBinding;

import java.util.Date;

public class InsertNoteActivity extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    String title, subtitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenpriority.setOnClickListener(v -> {
            binding.greenpriority.setImageResource(R.drawable.ic_done);
            binding.yellowpriority.setImageResource(0);
            binding.redpriority.setImageResource(0);
            priority = "1";
        });
        binding.yellowpriority.setOnClickListener(v -> {
            binding.greenpriority.setImageResource(0);
            binding.yellowpriority.setImageResource(R.drawable.ic_done);
            binding.redpriority.setImageResource(0);
            priority = "2";

        });
        binding.redpriority.setOnClickListener(v -> {
            binding.greenpriority.setImageResource(0);
            binding.yellowpriority.setImageResource(0);
            binding.redpriority.setImageResource(R.drawable.ic_done);
            priority = "3";

        });




        binding.doneNotesbtn.setOnClickListener(v -> {

            title = binding.notesTitle.getText().toString();
            subtitle = binding.notesSubTitle.getText().toString();
            notes = binding.notesData.getText().toString();

            CreateNotes(title, subtitle, notes);

            Animatoo.animateSlideRight(this);

        });
    }

    private void CreateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMM d, yyyy", date.getTime());


        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubtitle = subtitle;
        notes1.notes = notes;
        notes1.notesPriority = priority;
        notes1.notesDate = sequence.toString();

        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Note Create SuccessFully", Toast.LENGTH_SHORT).show();
        //Log.d("kramik","Note Create SuccessFully");
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();

        title = binding.notesTitle.getText().toString();
        subtitle = binding.notesSubTitle.getText().toString();
        notes = binding.notesData.getText().toString();

        CreateNotes(title, subtitle, notes);
        Animatoo.animateSlideRight(this);

    }
}