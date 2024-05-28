package ru.alphadrow.gb.mynotes;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MyDataBase implements Parcelable {

    static MyDataBase myDB = new MyDataBase();
    private static List<Note> noteList = new ArrayList<>();


    public MyDataBase() {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noteList.add(new Note("Вынести мусор", "Выносить мусор нужно каждый день.", LocalDate.of(1999, 1, 1), Importance.LIFE_AND_DEATH));
            noteList.add(new Note("Убить Билла", "Bill must die", LocalDate.of(2000, 2, 2), Importance.LOW));
            noteList.add(new Note("Захватить мир", "Что может быть проще?", LocalDate.of(2001, 3, 3), Importance.MEDIUM));
            noteList.add(new Note("Сбежать от санитаров", "Это уже сложнее...", LocalDate.of(1998, 4, 4), Importance.HIGH));
            noteList.add(new Note("Забыл", "Кто здесь?!", LocalDate.of(1997, 5, 5), Importance.FORGET_ABOUT_IT));
        }
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void put(Note note){
        noteList.add(note);
    }



    public Note get(int i){
        return noteList.get(i);
    }
}
