package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter{

        private ArrayList<Note> noteData;
        private View.OnClickListener mOnItemClickListener;
        private boolean isDeleting;
        private Context parentContext;
        public static int count = 0;


        public class ContactViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewNote;
            public TextView textImportance;
            public Button deleteButton;

            public ContactViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewNote = itemView.findViewById(R.id.textNoteSubject);
                textImportance = itemView.findViewById(R.id.textImportance);
                deleteButton = itemView.findViewById(R.id.buttonDeleteContact);
                itemView.setTag(this);
                itemView.setOnClickListener(mOnItemClickListener);
            }

            public TextView getNoteTextView() {
                return textViewNote;
            }

            public TextView getImportanceTextView() {
                return textImportance;
            }

            public Button getDeleteButton() {
                return deleteButton;
            }
        }

        public NotesAdapter(ArrayList<Note> arrayList, Context context) {
            noteData = arrayList;
            parentContext = context;
        }

        public void setOnItemClickListener(View.OnClickListener itemClickListener) {
            mOnItemClickListener = itemClickListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
            return new ContactViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ContactViewHolder cvh = (ContactViewHolder) holder;

            if(count % 3 == 0){
                count += 1;
                cvh.getNoteTextView().setTextColor(Color.parseColor("#009688"));
                cvh.getNoteTextView().setText(noteData.get(position).getNotesTitle());
            }
            else if(count % 3 == 1){
                cvh.getNoteTextView().setTextColor(Color.parseColor("#009688"));
                cvh.getNoteTextView().setText(noteData.get(position).getNotesTitle());
                count += 1;
            }
            else{
                count += 1;
                cvh.getNoteTextView().setTextColor(Color.parseColor("#009688"));
                cvh.getNoteTextView().setText(noteData.get(position).getNotesTitle());
            }

            cvh.getImportanceTextView().setText(noteData.get(position).getImportance());

            if (isDeleting) {
                cvh.getDeleteButton().setVisibility(View.VISIBLE);
                cvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteItem(position);
                    }
                });
            }
            else {
                cvh.getDeleteButton().setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return noteData.size();
        }


        private void deleteItem(int position) {
            Note note = noteData.get(position);
            NotesDataSource ds = new NotesDataSource(parentContext);
            try {
                ds.open();
                boolean didDelete = ds.deleteNote(note.getNoteID());
                ds.close();
                if(didDelete) {
                    noteData.remove(position);
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
                }

            }
            catch (Exception e) {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();

            }
        }

        public void setDelete(boolean b) {
            isDeleting = b;
        }

    }


