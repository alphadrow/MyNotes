package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NotesFragment extends Fragment {

     Note currentNote;

     boolean isLandScape;
     MyDataBase myDataBase;


    public static NotesFragment newInstance(){
        return new NotesFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
        myDataBase = MyDataBase.getInstance();
        if(savedInstanceState!=null){
            currentNote = savedInstanceState.getParcelable(Constants.KEY_NOTE);
            myDataBase = savedInstanceState.getParcelable(Constants.KEY_DB);
        }
//        if(isLandScape)
            if(currentNote !=null){
                showNoteProperties(currentNote);
            }else{
                showNoteProperties(myDataBase.get(0));
            }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.KEY_NOTE, currentNote);
        outState.putParcelable(Constants.KEY_DB, myDataBase);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes,container,false);
        LinearLayout linearLayout = (LinearLayout) view;




        for(int i =0;i<myDataBase.getNoteList().size();i++){
            String name = myDataBase.getNoteList().get(i).getName();
            TextView textView = new TextView(getContext());
            textView.setText(name);
            textView.setTextSize(30);
            linearLayout.addView(textView);
            int finalI1 = i;
            textView.setOnClickListener(v -> showNoteProperties(myDataBase.getNoteList().get(finalI1)));
        }

        return view;
    }

    private void showNoteProperties(Note note) {
            currentNote = note;
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
}
