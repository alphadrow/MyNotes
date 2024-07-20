package ru.alphadrow.gb.mynotes;

import java.util.List;

public interface MyDataBaseInterface {
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    public List<Note> getNoteList();
    public Note getNote(int i);
}
