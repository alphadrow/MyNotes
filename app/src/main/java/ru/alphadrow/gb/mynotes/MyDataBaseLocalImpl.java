package ru.alphadrow.gb.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDataBaseLocalImpl implements Parcelable, NotesSource {

    static MyDataBaseLocalImpl myDB = new MyDataBaseLocalImpl();
    private static List<Note> noteList = new ArrayList<>();


    public MyDataBaseLocalImpl() {
    }

    public int size(){
        return noteList.size();
    }

    protected MyDataBaseLocalImpl(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyDataBaseLocalImpl> CREATOR = new Creator<MyDataBaseLocalImpl>() {
        @Override
        public MyDataBaseLocalImpl createFromParcel(Parcel in) {
            return new MyDataBaseLocalImpl(in);
        }

        @Override
        public MyDataBaseLocalImpl[] newArray(int size) {
            return new MyDataBaseLocalImpl[size];
        }
    };

    public static MyDataBaseLocalImpl getInstance() {
        if (noteList.isEmpty()) {
            setList();
        }
        return myDB;
    }


    private static void setList() {

        noteList.add(new Note("Вынести мусор", "Выносить мусор нужно каждый день.", new Date(1999, 1, 1), Importance.LIFE_AND_DEATH));
            noteList.add(new Note("Убить Билла", "Bill must die", new Date(2000, 2, 2), Importance.LOW));
            noteList.add(new Note("Захватить мир", "Что может быть проще?", new Date(2000, 3, 3), Importance.MEDIUM));
            noteList.add(new Note("Сбежать от санитаров", "Это уже сложнее...", new Date(2000, 4, 4), Importance.HIGH));
            noteList.add(new Note("Забыл", "Кто здесь?!", new Date(2000, 4, 4), Importance.FORGET_ABOUT_IT));

    }



    @Override
    public void deleteNote(int position) {
        noteList.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        noteList.set(position, note);
    }

    @Override
    public void addNote(Note newNote) {
        noteList.add(newNote);
    }

    @Override
    public int getNoteId(Note note) {
        return noteList.indexOf(note);
    }

    @Override
    public NotesSource init(NotesSourceResponse notesSourceResponse) {
        return myDB;
    }


    @Override
    public Note getNote(int position){
        return noteList.get(position);
    }
}
