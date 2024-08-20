package ru.alphadrow.gb.mynotes;

public interface NotesSource {
    NotesSource init(NotesSourceResponse notesSourceResponse);
    Note getNote(int position);
    int size();
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note newNote);

    public int getNoteId(Note note);

}
