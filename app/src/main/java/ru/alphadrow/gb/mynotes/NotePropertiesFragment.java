package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NotePropertiesFragment extends Fragment {

    public static String ARG_NOTE = "note";
    Note currentNote;
    boolean isLandScape;

    public static NotePropertiesFragment newInstance(Note note){
        NotePropertiesFragment fragment=  new NotePropertiesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
        if(getArguments()!=null){
            this.currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_properties,container,false);
        TextView textView = view.findViewById(R.id.nameTextView);
        textView.setText(this.currentNote.getName());
        TextView description = view.findViewById(R.id.descriptionTextView);
        description.setText(this.currentNote.getDescription());
        TextView dateOfCreate = view.findViewById(R.id.timeAndDateTextView);
        dateOfCreate.setText(this.currentNote.getDateOfCreation().toString());
        dateOfCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(currentNote);
            }
        });
        TextView importance = view.findViewById(R.id.importanceTextView);
        importance.setText(this.currentNote.getImportance().toString());

        return view;
    }

    private void showDatePicker(Note note) {
        currentNote = note;
        if (isLandScape) {
            showDatePickerLand(note);
        }else{
            showDatePickerPort(note);
        }
    }

    private void showDatePickerPort(Note note) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notesContainer, NoteDatePickerFragment.newInstance(note))
                .addToBackStack("")
                .commit();
    }

    private void showDatePickerLand(Note note) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notePropertiesContainer, NoteDatePickerFragment.newInstance(note))
                .addToBackStack("")
                .commit();
    }
}
