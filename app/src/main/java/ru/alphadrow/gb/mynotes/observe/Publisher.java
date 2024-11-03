package ru.alphadrow.gb.mynotes.observe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.alphadrow.gb.mynotes.Note;

public class Publisher {
    private LinkedList<Observer> observers;

    public  Publisher(){
        observers = new LinkedList<Observer>();
    }

    public void subscribe(Observer observer) {
        observers.push(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyTask(Note note){
        if (!observers.isEmpty()) {
            observers.poll().updateState(note);
        }
    }

}
