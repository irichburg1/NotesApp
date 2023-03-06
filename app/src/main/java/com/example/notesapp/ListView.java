package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        initListButton();
        initAddButton();
        initSettingsButton();
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
}