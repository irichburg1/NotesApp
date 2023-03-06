package com.example.notesapp;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListButton();
        initAddButton();
        initSettingsButton();
        initToggleButton();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initNotes(extras.getInt("noteId"));
        }
        else {
            currentNote = new Note();
        }

        setForEditing(false);
        initTextChangedEvents();
        radioButtonChanged();
        initSaveButton ();
    }

    //TOGGLEBUTTON for EDITING
    private void initToggleButton() {
        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(editToggle.isChecked());
            }
        });
    }

    private void setForEditing(boolean enabled) {
        EditText editTitle = findViewById(R.id.editTextTitle);
        EditText editContent = findViewById(R.id.editTextContent);
        RadioGroup rgroup = findViewById(R.id.radioGroupSort);
        RadioButton rbHigh = findViewById(R.id.radioButtonHigh);
        RadioButton rbMedium = findViewById(R.id.radioButtonMedium);
        RadioButton rbLow = findViewById(R.id.radioButtonLow);
        Button buttonSave = findViewById(R.id.buttonSave);

        //ImageButton picture = findViewById(R.id.imageContact);

        editTitle.setEnabled(enabled);
        editContent.setEnabled(enabled);
        rgroup.setEnabled(enabled);
        buttonSave.setEnabled(enabled);
        rbHigh.setEnabled(enabled);
        rbMedium.setEnabled(enabled);
        rbLow.setEnabled(enabled);

        if (enabled) {
            editTitle.requestFocus();
        } else {
            ScrollView s = findViewById(R.id.scrollview);
            s.fullScroll(ScrollView.FOCUS_UP);
        }
    }


    //Beginning of Navigation Bar Controls
    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initAddButton() {
        ImageButton ibList = findViewById(R.id.imageButtonAdd);
        ibList.setEnabled(false);
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initTextChangedEvents() {
        final EditText etNoteName = findViewById(R.id.editTextTitle);
        etNoteName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable a) {
                currentNote.setNotesTitle(etNoteName.getText().toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        final EditText etNoteContent = findViewById(R.id.editTextContent);
        etNoteContent.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentNote.setNotesContent(etNoteContent.getText().toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

    }

    private void radioButtonChanged(){
        RadioButton rbHigh = findViewById(R.id.radioButtonHigh);
        RadioButton rbMedium = findViewById(R.id.radioButtonMedium);
        RadioButton rbLow = findViewById(R.id.radioButtonLow);

        if (rbHigh.isChecked()) {
            currentNote.setImportance(rbHigh.getText().toString());
        }
        else if (rbMedium.isChecked()){
            currentNote.setImportance(rbMedium.getText().toString());
        }
        else{
            currentNote.setImportance(rbLow.getText().toString());
        }
    }

    private void initSaveButton() {
        Button saveButton = findViewById (R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                boolean wasSuccessful;
                hideKeyboard();
                NotesDataSource ds = new NotesDataSource(MainActivity.this);
                try {
                    ds.open();

                    if (currentNote.getNoteID() == -1) {
                        wasSuccessful = ds.insertNotes(currentNote);
                        if (wasSuccessful) {
                            int newId = ds.getLastNoteId();
                            currentNote.setNoteID(newId);
                        }
                    }
                    else {
                        wasSuccessful = ds.updateNotes(currentNote);
                    }
                    ds.close();
                }
                catch (Exception e) {
                    wasSuccessful = false;
                }
                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }

    private void hideKeyboard () {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editName=findViewById(R.id.editTextTitle);
        imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
        EditText editAddress=findViewById(R.id.editTextContent);
        imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
    }

    private void initNotes(int id) {
        NotesDataSource ds = new NotesDataSource(MainActivity.this);
        try {
            ds.open();
            currentNote = ds.getSpecificNote(id);
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this, "Load Contact Failed", Toast.LENGTH_LONG).show();
        }

        EditText editTitle = findViewById(R.id.editTextTitle);
        EditText editContent = findViewById(R.id.editTextContent);


        editTitle.setText(currentNote.getNotesTitle());
        editContent.setText(currentNote.getNotesContent());

    }

}