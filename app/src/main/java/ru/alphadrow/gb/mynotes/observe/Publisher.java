package ru.alphadrow.gb.mynotes.observe;

import java.util.ArrayList;
import java.util.List;

import ru.alphadrow.gb.mynotes.Note;

public class Publisher {
    private List<Observer> observers;

    public  Publisher(){
        observers = new ArrayList<Observer>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyTask(Note note){
        for (Observer observer: observers) {
            observer.updateState(note);
            unsubscribe(observer); //TODO понять почему мы отписываемся
        }
    }
}
