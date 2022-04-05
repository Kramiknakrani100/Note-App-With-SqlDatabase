package com.example.noteappmvvm.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.noteappmvvm.Model.Notes;
import com.example.noteappmvvm.R;
import com.example.noteappmvvm.ViewModel.NotesViewModel;
import com.example.noteappmvvm.databinding.ActivityUpdateNoteBinding;

import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    String priority = "1";
    String sTitle, sSubTitle, sNotes, sPriority;
    int sId;
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        sTitle = getIntent().getStringExtra("title");
        sId = getIntent().getIntExtra("id",0);
        sSubTitle = getIntent().getStringExtra("subtitle");
        sNotes = getIntent().getStringExtra("notes");
        sPriority = getIntent().getStringExtra("priority");

        binding.updateTitle.setText(sTitle);
        binding.updatesunTitle.setText(sSubTitle);
        binding.updateNotes.setText(sNotes);

        if (sPriority.equals("1")) {
            binding.greenpriority.setImageResource(R.drawable.ic_done);
        }
        if (sPriority.equals("2")) {
            binding.yellowpriority.setImageResource(R.drawable.ic_done);
        }
        if (sPriority.equals("3")) {
            binding.redpriority.setImageResource(R.drawable.ic_done);
        }

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

        binding.updateNotesbtn.setOnClickListener(v -> {

            String title = binding.updateTitle.getText().toString();
            String subtitle = binding.updatesunTitle.getText().toString();
            String notes = binding.updateNotes.getText().toString();

            UpdateNotes(title, subtitle, notes);
            Animatoo.animateSlideDown(this);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_TEXT, "Title:- " + sTitle + "\nSubtitle:- " + sSubTitle + "\nNote:- " + sNotes);
            intent.setType("jpg/plain");
            intent.setType("text/plain");
            Intent chooser = Intent.createChooser(intent, "Share This Note...");
            startActivity(chooser);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void UpdateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMM d, yyyy", date.getTime());

        Notes updateNotes = new Notes();
        updateNotes.id = sId;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subtitle;
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);

        Toast.makeText(UpdateNoteActivity.this, "Note Updated SuccessFully", Toast.LENGTH_SHORT).show();
        //Log.d("kramik","Note Create SuccessFully");
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();

        String title = binding.updateTitle.getText().toString();
        String subtitle = binding.updatesunTitle.getText().toString();
        String notes = binding.updateNotes.getText().toString();

        UpdateNotes(title, subtitle, notes);

        Animatoo.animateSlideDown(this);

    }
}