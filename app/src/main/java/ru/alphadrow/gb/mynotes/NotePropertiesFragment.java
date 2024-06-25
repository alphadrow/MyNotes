package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotePropertiesFragment extends Fragment implements MyOnClickListener {

    public static String ARG_NOTE = "note";
    Note currentNote;
    boolean isLandScape;

    public static NotePropertiesFragment newInstance(Note note) {
        NotePropertiesFragment fragment = new NotePropertiesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (getArguments() != null) {
            this.currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_properties, container, false);


        return view;
    }

    private void showDatePicker() {

        if (isLandScape) {
            showDatePickerLand(currentNote);
        } else {
            showDatePickerPort(currentNote);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView textView = view.findViewById(R.id.nameTextView);
        Button buttonEdit = view.findViewById(R.id.editButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.notesContainer, NotePropertiesFragmentEdit.newInstance(currentNote))
                        .addToBackStack("")
                        .commit();
            }
        });
        TextView description = view.findViewById(R.id.descriptionTextView);
        TextView dateOfCreate = view.findViewById(R.id.timeAndDateTextView);
        dateOfCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        TextView importance = view.findViewById(R.id.importanceTextView);
        textView.setText(this.currentNote.getName());
        description.setText(this.currentNote.getDescription());
        dateOfCreate.setText(this.currentNote.getDateOfCreation().toString());
        importance.setText(this.currentNote.getImportance().toString());
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

    @Override
    public void onMyClick(View view, int position) {
        showDatePicker();
    }
}
