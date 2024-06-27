package ru.alphadrow.gb.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDataBase implements Parcelable, MyDataBaseInterface {

    static MyDataBase myDB = new MyDataBase();
    private static List<Note> noteList = new ArrayList<>();


    public MyDataBase() {
    }

    public int size(){
        return noteList.size();
    }

    protected MyDataBase(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyDataBase> CREATOR = new Creator<MyDataBase>() {
        @Override
        public MyDataBase createFromParcel(Parcel in) {
            return new MyDataBase(in);
        }

        @Override
        public MyDataBase[] newArray(int size) {
            return new MyDataBase[size];
        }
    };

    public static MyDataBase getInstance() {
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
    public void addNote(Note note) {
        noteList.add(note);
    }
    @Override
    public List<Note> getNoteList() {
        return noteList;
    }

    @Override
    public Note getNote(int i){
        return noteList.get(i);
    }
}
