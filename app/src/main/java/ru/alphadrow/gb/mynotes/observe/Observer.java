package ru.alphadrow.gb.mynotes.observe;

import ru.alphadrow.gb.mynotes.Note;

public interface Observer {
    void updateState(Note note);

}
