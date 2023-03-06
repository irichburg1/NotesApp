package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class ListView extends AppCompatActivity {

    ArrayList<Note> notes;
    NotesAdapter noteAdapter;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int noteId = notes.get(position).getNoteID();
            Intent intent = new Intent(ListView.this, MainActivity.class);
            intent.putExtra("noteId", noteId);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        initListButton();
        initAddButton();
        initSettingsButton();

        String sortBy = getSharedPreferences("MyNotesPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "subjectname");
        String sortOrder = getSharedPreferences("MyNotesPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        NotesDataSource ds = new NotesDataSource(this);

        try {
            ds.open();
            notes = ds.getNotes(sortBy, sortOrder);
            ds.close();
            RecyclerView contactList = findViewById(R.id.rvNotes);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); //Puts viewholder into recycle view
            contactList.setLayoutManager(layoutManager); //sets the layout manager for the contact list
            noteAdapter = new NotesAdapter(notes, this); //Puts the name data into the rvContacts
            contactList.setAdapter(noteAdapter); //setting the adapter for the contact list (runs other methods in view holder class too)
            noteAdapter.setOnItemClickListener(onItemClickListener); //sets an onclicklistener for the contact list
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving notes", Toast.LENGTH_LONG).show();
        }

        initDeleteSwitch();
        initAddNoteButton();
    }

    @Override
    public void onResume() {
        super.onResume();

        String sortBy = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        NotesDataSource ds = new NotesDataSource(this);
        try {
            ds.open();
            notes = ds.getNotes(sortBy, sortOrder);
            ds.close();
            if (notes.size() > 0) {
                RecyclerView contactList = findViewById(R.id.rvNotes);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                contactList.setLayoutManager(layoutManager);
                noteAdapter = new NotesAdapter(notes, this);
                noteAdapter.setOnItemClickListener(onItemClickListener);
                contactList.setAdapter(noteAdapter);
            }
            else{
                Intent intent = new Intent(ListView.this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }


    private void initListButton() {
        ImageButton ibList = findViewById (R.id.imageButtonList);
        ibList.setEnabled(false);
    }

    private void initAddButton() {
        ImageButton ibList = findViewById (R.id.imageButtonAdd);
        ibList.setOnClickListener (new View.OnClickListener () {
            public void onClick (View view) {
                Intent intent = new Intent (ListView.this, MainActivity.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener (new View.OnClickListener () {
            public void onClick (View view) {
                Intent intent = new Intent (ListView.this, Settings.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
            }
        });
    }

    private void initAddNoteButton() {
        Button ibNewNote = findViewById(R.id.buttonAddNote);
        ibNewNote.setOnClickListener (new View.OnClickListener () {
            public void onClick (View view) {
                Intent intent = new Intent (ListView.this, MainActivity.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
            }
        });
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = compoundButton.isChecked();
                noteAdapter.setDelete(status);
                noteAdapter.notifyDataSetChanged();

            }
        });
    }

}