package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_notes);

        initListButton();
        initAddButton();
        initSettingsButton();
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

}
