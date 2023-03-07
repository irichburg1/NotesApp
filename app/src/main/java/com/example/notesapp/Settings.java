package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_notes);

        initListButton();
        initAddButton();
        initSettingsButton();

        initSettings();
        initSortByClick();
        initSortOrderClick();
    }

    private void initListButton() {
        ImageButton ibList = findViewById (R.id.imageButtonList);
        ibList.setOnClickListener (new View.OnClickListener () {
            public void onClick (View view) {
                Intent intent = new Intent (Settings.this, ListView.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
            }
        });
    }
    private void initAddButton() {
        ImageButton ibList = findViewById (R.id.imageButtonAdd);
        ibList.setOnClickListener (new View.OnClickListener () {
            public void onClick (View view) {
                Intent intent = new Intent (Settings.this, MainActivity.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setEnabled(false);
    }

    private void initSettings(){
        String sortBy = getSharedPreferences("MyNotesPreferences",
                Context.MODE_PRIVATE).getString("sortfield","notestitle");
        String sortOrder = getSharedPreferences("MyNotesPreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbSubject = findViewById(R.id.radioSubject);
        RadioButton rbImportance = findViewById(R.id.radioImportance);
        RadioButton rbDate = findViewById(R.id.radioDate);
        if(sortBy.equalsIgnoreCase("notestitle")) {
            rbSubject.setChecked(true);
        }
        else if (sortBy.equalsIgnoreCase("importance")) {
            rbImportance.setChecked(true);
        }
        else{
            rbDate.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);
        if(sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else{
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbSubject = findViewById(R.id.radioSubject);
                RadioButton rbImportance = findViewById(R.id.radioImportance);
                if(rbSubject.isChecked()) {
                    getSharedPreferences("MyNotesPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortfield","notestitle").apply();
                }
                else if (rbImportance.isChecked()) {
                    getSharedPreferences("MyNotesPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortfield","importance").apply();
                }
                else{
                    getSharedPreferences("MyNotesPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortfield","date").apply();
                }
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbAscending = findViewById(R.id.radioAscending);
                if(rbAscending.isChecked()) {
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortorder","ASC").apply();
                }
                else{
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit()
                            .putString("sortorder","DESC").apply();
                }
            }
        });
    }

}
