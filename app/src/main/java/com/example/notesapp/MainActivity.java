package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListButton();
        initAddButton();
        initSettingsButton();
    }

    private void initListButton() {
        ImageButton ibList = findViewById (R.id.imageButtonList);
        ibList.setOnClickListener (new View.OnClickListener () {
            public void onClick (View view) {
                Intent intent = new Intent (MainActivity.this, ListView.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
            }
        });
    }

    private void initAddButton() {
        ImageButton ibList = findViewById (R.id.imageButtonAdd);
        ibList.setEnabled(false);
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener (new View.OnClickListener () {
            public void onClick (View view) {
                Intent intent = new Intent (MainActivity.this, Settings.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
            }
        });
    }
}