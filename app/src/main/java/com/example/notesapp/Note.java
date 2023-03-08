package com.example.notesapp;


import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Note {

    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    private int noteID;
    private String notesTitle;
    private String notesContent;
    private int importance;
    private String date;

    public Note () {
        noteID = -1;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public int getImportance() { return importance;}

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getNotesContent() {
        return notesContent;
    }

    public void setNotesContent(String notesContent) {
        this.notesContent = notesContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = formatter.format(date);

    }
}



