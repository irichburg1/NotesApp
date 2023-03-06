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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListButton();
        initAddButton();
        initSettingsButton();

        initToggleButton ();
        setForEditing (false);


    }

    //TOGGLEBUTTON for EDITING
    private void initToggleButton () {
        final ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                setForEditing (editToggle.isChecked ());
            }
        });
    }
    private void setForEditing (boolean enabled) {
        EditText editTitle = findViewById(R.id.editTextTitle);
        EditText editContent = findViewById(R.id.editTextContent);

        Button buttonSave = findViewById (R.id.buttonSave);

        //ImageButton picture = findViewById(R.id.imageContact);

        editTitle.setEnabled(enabled);
        editContent.setEnabled(enabled);
        //editPhone.setEnabled (enabled);
        //editCell.setEnabled (enabled);
        buttonSave.setEnabled (enabled);

        if (enabled) {
            editTitle.requestFocus();
        } else {
            ScrollView s = findViewById (R.id.scrollview);
            s.fullScroll (ScrollView.FOCUS_UP);
        }
    }



    //Beginning of Navigation Bar Controls
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