package ru.alphadrow.gb.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

interface NotesSource {
    void init(NotesSourceResponse notesSourceResponse);

    Note getNote(int position);

    int size();

    void deleteNote(int position);

    void updateNote(int position, Note note);

    void addNote(Note newNote);


    public int getNoteId(Note note);

}


