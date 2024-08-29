package ru.alphadrow.gb.mynotes;

import android.content.Context;
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

import ru.alphadrow.gb.mynotes.observe.Publisher;

public class NotePropertiesFragment extends Fragment {

    public static String ARG_NOTE = "note";
    Note currentNote;
    boolean isLandScape;


    TextView nameTextView;
    Button buttonEdit;

    TextView description;
    TextView dateOfCreate;

    TextView importance;

    private Publisher publisher;
    private Navigation navigation;


    public static NotePropertiesFragment newInstance(Note note) {
        NotePropertiesFragment fragment = new NotePropertiesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_properties, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (getArguments() != null) {
            this.currentNote = getArguments().getParcelable(ARG_NOTE);
        }
        initContent(view);
        setContent();
    }

    private void setContent() {
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
        nameTextView.setText(this.currentNote.getName());
        description.setText(this.currentNote.getDescription());
        dateOfCreate.setText(this.currentNote.getDateOfCreation().toString());
        importance.setText(this.currentNote.getImportance().toString());
    }

    private void initContent(@NonNull View view) {
        nameTextView = view.findViewById(R.id.nameTextView);
        buttonEdit = view.findViewById(R.id.editButton);
        description = view.findViewById(R.id.descriptionTextView);
        dateOfCreate = view.findViewById(R.id.timeAndDateTextView);
        importance = view.findViewById(R.id.importanceTextView);
    }
}
