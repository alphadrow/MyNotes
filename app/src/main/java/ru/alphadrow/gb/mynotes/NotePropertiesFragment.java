package ru.alphadrow.gb.mynotes;

import android.app.DatePickerDialog;
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
import androidx.fragment.app.FragmentTransaction;

import ru.alphadrow.gb.mynotes.observe.Observer;
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

    }

    @Override
    public void onDetach() {
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
        MainActivity activity = (MainActivity) requireActivity();
        navigation = activity.getNavigation();
        publisher = MyApp.getPublisher();
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (getArguments() != null) {
            this.currentNote = getArguments().getParcelable(ARG_NOTE);
        }
        initContent(view);
        setContent();
    }

    private void setContent() {
        nameTextView.setText(this.currentNote.getName());
        description.setText(this.currentNote.getDescription());
        dateOfCreate.setText(this.currentNote.getDateOfCreation().toString());
        importance.setText(this.currentNote.getImportance().toString());
        buttonEdit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        currentNote = note;
                    }


                });
                navigation.addFragment(NotePropertiesEditFragment.newInstance(currentNote), true, "notePropertiesEditFragment_onEditButton");

            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        currentNote.setDescription(note.getDescription());
                        description.setText(note.getDescription());
                    }

                });

                DescriptionEditFragment descriptionEditFragment = new DescriptionEditFragment();
                descriptionEditFragment.show(requireActivity().getSupportFragmentManager(), "TAG");
            }
        });
        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        currentNote.setName(note.getName());
                        nameTextView.setText(note.getName());
                    }

                });

                NameEditFragment nameEditFragment = new NameEditFragment();
                nameEditFragment.show(requireActivity().getSupportFragmentManager(), "TAG");
            }
        });
        importance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        currentNote.setImportance(note.getImportance());
                        importance.setText(note.getImportance().toString());
                    }

                });
                ImportanceEditFragment importanceEditFragment = new ImportanceEditFragment();
                importanceEditFragment.show(requireActivity().getSupportFragmentManager(), "TAG");
            }
        });
        dateOfCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        currentNote.setDateOfCreation(note.getDateOfCreation());
                        dateOfCreate.setText(note.getDateOfCreation().toString());
                    }

                });
                DateOfCreationEditFragment dateOfCreationEditFragment = new DateOfCreationEditFragment();
                dateOfCreationEditFragment.show(requireActivity().getSupportFragmentManager(), "TAG");
            }
        });
    }


    private void initContent(@NonNull View view) {
        nameTextView = view.findViewById(R.id.nameTextView);
        buttonEdit = view.findViewById(R.id.editButton);
        description = view.findViewById(R.id.descriptionTextView);
        dateOfCreate = view.findViewById(R.id.timeAndDateTextView);
        importance = view.findViewById(R.id.importanceTextView);
    }

    @Override
    public void onDestroy() {
        publisher.notifyTask(currentNote);
        super.onDestroy();
    }
}
