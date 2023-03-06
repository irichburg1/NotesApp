package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class NotesDataSource {


    private SQLiteDatabase database;
    private NotesDBHelper dbHelper;

    public NotesDataSource (Context context) {
        dbHelper = new NotesDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public boolean insertNotes(Note n) {
        boolean didSucceed = false;
        try{
            ContentValues initialValues = new ContentValues();

            initialValues.put("notesTitle",n.getNotesTitle());
            initialValues.put("notesContent",n.getNotesContent());
            if (n.getPicture() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                n.getPicture().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                initialValues.put("notesPicture", photo);
            }

            didSucceed = database.insert("notes",null,initialValues) > 0;
        }
        catch (Exception e){
            //Do nothing - will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateNotes(Note n) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) n.getNoteID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("notesTitle", n.getNotesTitle());
            updateValues.put("notesContent", n.getNotesContent());

            if (n.getPicture() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                n.getPicture().compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] photo = baos.toByteArray();
                updateValues.put("notesPicture", photo);
            }

            didSucceed = database.update("notes", updateValues, "_id=" + rowId, null) > 0;
        } catch (Exception e) {
            //Do nothing - will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastNoteId() {
        int lastId;
        try {
            String query = "Select MAX(_id) from notes";
            Cursor cursor = database.rawQuery (query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<String> getNoteTitle() {
        ArrayList<String> noteTitles = new ArrayList<>();
        try {
            String query = "Select notesTitle from notes";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                noteTitles.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            noteTitles = new ArrayList<String>();
        }
        return noteTitles;
    }

    public ArrayList<Note> getContacts(String sortField, String sortOrder) {

        ArrayList<Note> notes = new ArrayList<Note>();
        try {
            String query = "SELECT * FROM notes ORDER BY " + sortField + " " + sortOrder;

            Cursor cursor = database.rawQuery(query, null);

            Note newNote;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newNote = new Note();
                newNote.setNoteID(cursor.getInt(0));
                newNote.setNotesTitle(cursor.getString(1));
                newNote.setNotesContent(cursor.getString(2));
                notes.add(newNote);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            notes = new ArrayList<Note>();
        }
        return notes;
    }

    public Note getSpecificContact(int noteId) {
        Note currentNote = new Note();
        String query = "SELECT  * FROM notes WHERE _id =" + noteId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            currentNote.setContactID(cursor.getInt(0));
            currentNote.setContactName(cursor.getString(1));
            currentNote.setStreetAddress(cursor.getString(2));

            byte[] photo = cursor.getBlob(10);
            if (photo != null) {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                Bitmap thePicture= BitmapFactory.decodeStream(imageStream);
                contact.setPicture(thePicture);
            }
            cursor.close();
        }
        return contact;
    }

    public boolean deleteContact(int contactId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("contact", "_id=" + contactId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }




}
