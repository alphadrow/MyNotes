package ru.alphadrow.gb.mynotes;

import android.app.Application;

import ru.alphadrow.gb.mynotes.observe.Publisher;

public class MyApp extends Application {
    private static Publisher publisher;

    @Override
    public void onCreate() {
        super.onCreate();
        publisher = new Publisher();
    }

    public static Publisher getPublisher() {
        return publisher;
    }
}
