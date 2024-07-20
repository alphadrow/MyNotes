package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteFragment extends Fragment implements MyOnClickListener{

     Note currentNote;

     boolean isLandScape;
     MyDataBase myDataBase;


    public static NoteFragment newInstance(){
        return new NoteFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
        myDataBase = MyDataBase.getInstance();
        if(savedInstanceState!=null){
            currentNote = savedInstanceState.getParcelable(Settings.KEY_NOTE);
            myDataBase = savedInstanceState.getParcelable(Settings.KEY_DB);
        }
        if(isLandScape)
            if(currentNote !=null){
                showNoteProperties(currentNote);
            }else{
                showNoteProperties(myDataBase.getNote(0));
            }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Settings.KEY_NOTE, currentNote);
        outState.putParcelable(Settings.KEY_DB, myDataBase);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes,container,false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        NoteAdapter noteAdapter = new NoteAdapter(getTextViewArray());
        noteAdapter.setOnMyOnClickListener(this);
        recyclerView.setAdapter(noteAdapter);
    }

    private String[] getTextViewArray() {
        String[] resultArray = new String[myDataBase.getNoteList().size()];
        for(int i =0;i<myDataBase.getNoteList().size();i++){

            String name = myDataBase.getNoteList().get(i).getName();
            resultArray[i] = name;
        }
        return  resultArray;
    }


    private void showNoteProperties(Note note) {
            currentNote = note;
        Log.d("myLogs", "currentNote.getName() :" + currentNote.getName());

        if (isLandScape) {
            showNotePropertiesLand();
        }else{
            showNotePropertiesPort();
        }
    }

    private void showNotePropertiesPort() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notesContainer, NotePropertiesFragment.newInstance(currentNote))
                .addToBackStack("")
                .commit();
    }

    private void showNotePropertiesLand() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notePropertiesContainer, NotePropertiesFragment.newInstance(currentNote))
                .commit();
    }

    @Override
    public void onMyClick(View view, int position) {
        showNoteProperties(myDataBase.getNote(position));
    }

}
