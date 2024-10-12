package ru.alphadrow.gb.mynotes;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseFirebaseImpl implements NotesSource{
    private static String NOTES_COLLECTION = "notes";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference  collectionReference = store.collection(NOTES_COLLECTION);
    private  List<Note> notes = new ArrayList<Note>();


    @Override
    public void init(NotesSourceResponse notesSourceResponse) {
        
        collectionReference.orderBy(NoteTranslate.Fields.DATE_OF_CREATION, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            notes = new ArrayList<Note>();
                            for (QueryDocumentSnapshot docFields : task.getResult()) {
                                Note note = NoteTranslate.documentToNote(docFields.getId(),
                                        docFields.getData());
                                notes.add(note);
                            }
                            notesSourceResponse.initialazed(notes);
                        }
                    }
                });
    }

    @Override
    public void deleteNote(int position) {
        collectionReference.document(notes.get(position).getId()).delete();
    }

    @Override
    public void updateNote(int position, Note note) {
        collectionReference.document(notes.get(position).getId())
                .update(NoteTranslate.noteToDocument(note));
    }

    @Override
    public void addNote(Note newNote) {
        collectionReference.add(NoteTranslate.noteToDocument(newNote));
    }

    @Override
    public int getNoteId(Note note) {
        return notes.indexOf(note);
    }


    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }
}
