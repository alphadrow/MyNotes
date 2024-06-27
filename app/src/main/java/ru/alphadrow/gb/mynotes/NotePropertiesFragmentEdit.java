package ru.alphadrow.gb.mynotes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotePropertiesFragmentEdit extends Fragment implements MyOnClickListener {
    Note currentNote;
    boolean isLandScape;
    Importance importance;
    RadioGroup radioGroup;
    RadioButton forgetAboutItImportanceRB;
    RadioButton lowImportanceRB;
    RadioButton mediumImportanceRB;
    RadioButton highImportanceRB;
    RadioButton lifeAndDeathImportanceRB;
    Button applyButton;
    Button editButton;
    EditText name;
    EditText description;
    public static String ARG_NOTE = "note";


    public static NotePropertiesFragmentEdit newInstance(Note note) {
        NotePropertiesFragmentEdit fragment = new NotePropertiesFragmentEdit();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (getArguments() != null) {
            this.currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_properties_edit, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initContent(view);
        setContent();
    }

    private void setContent() {
        description.setText(this.currentNote.getDescription());
        name.setText(this.currentNote.getName());
        forgetAboutItImportanceRB.setText(Importance.FORGET_ABOUT_IT.toString());
        lowImportanceRB.setText(Importance.LOW.toString());
        mediumImportanceRB.setText(Importance.MEDIUM.toString());
        highImportanceRB.setText(Importance.HIGH.toString());
        lifeAndDeathImportanceRB.setText(Importance.LIFE_AND_DEATH.toString());
        radioGroup.check(getRealRBID(currentNote.getImportance()));
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote.setName(name.getText().toString());
                currentNote.setDescription(description.getText().toString());
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == forgetAboutItImportanceRB.getId()) {
                    currentNote.setImportance(Importance.FORGET_ABOUT_IT);
                }
                if (checkedId == lowImportanceRB.getId()) {
                    currentNote.setImportance(Importance.LOW);
                }
                if (checkedId == mediumImportanceRB.getId()) {
                    currentNote.setImportance(Importance.MEDIUM);
                }
                if (checkedId == highImportanceRB.getId()) {
                    currentNote.setImportance(Importance.HIGH);
                }
                if (checkedId == lifeAndDeathImportanceRB.getId()) {
                    currentNote.setImportance(Importance.LIFE_AND_DEATH);
                }
            }
        });
    }

    private int getRealRBID(Importance importance) {
        switch (importance) {
            case FORGET_ABOUT_IT:
                return R.id.forgetAboutItImportanceRB;
            case LOW:
                return R.id.lowImportanceRB;
            case MEDIUM:
                return R.id.mediumImportanceRB;
            case HIGH:
                return R.id.highImportanceRB;
            case LIFE_AND_DEATH:
                return R.id.lifeAndDeathImportanceRB;
            default:
                return R.id.forgetAboutItImportanceRB;
        }
    }

    private void initContent(@NonNull View view) {
        name = view.findViewById(R.id.nameEditText);
        description = view.findViewById(R.id.descriptionEditText);
        applyButton = view.findViewById(R.id.applyButton);
        radioGroup = view.findViewById(R.id.importanceRG);
        forgetAboutItImportanceRB = view.findViewById(R.id.forgetAboutItImportanceRB);
        lowImportanceRB = view.findViewById(R.id.lowImportanceRB);
        mediumImportanceRB = view.findViewById(R.id.mediumImportanceRB);
        highImportanceRB = view.findViewById(R.id.highImportanceRB);
        lifeAndDeathImportanceRB = view.findViewById(R.id.lifeAndDeathImportanceRB);
        editButton = view.findViewById(R.id.editDate);
    }


    @Override
    public void onMyClick(View view, int position) {

    }

    protected void showDatePicker() {

        if (isLandScape) {
            showDatePickerLand(currentNote);
        } else {
            showDatePickerPort(currentNote);
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
