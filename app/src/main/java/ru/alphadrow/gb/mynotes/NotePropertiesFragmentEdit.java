package ru.alphadrow.gb.mynotes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

import ru.alphadrow.gb.mynotes.observe.Publisher;

public class NotePropertiesFragmentEdit extends Fragment implements MyOnClickListener {

    private Publisher publisher;

    NotesSource notesSource;
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

    public static NotePropertiesFragmentEdit newInstance() {
        NotePropertiesFragmentEdit fragment = new NotePropertiesFragmentEdit();
        return fragment;
    }


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
        } else {
            this.currentNote = new Note(""
                    , ""
                    , new Date()
                    , Importance.FORGET_ABOUT_IT);
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


    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            currentNote.setDateOfCreation(new Date(
                    datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth()));
        }
    };

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        Calendar date = Calendar.getInstance();

        new DatePickerDialog(requireContext(),
                d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
        )
                .show();
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
                int index = notesSource.getNoteId(currentNote);
                currentNote = collectNote();
                if (index < 0){
                    notesSource.addNote(currentNote);
                } else {
                    notesSource.updateNote(index, currentNote);
                }
                publisher.notifyTask(currentNote);
                requireActivity().getOnBackPressedDispatcher().onBackPressed();

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == forgetAboutItImportanceRB.getId()) {
                    importance = (Importance.FORGET_ABOUT_IT);
                }
                if (checkedId == lowImportanceRB.getId()) {
                    importance = (Importance.LOW);
                }
                if (checkedId == mediumImportanceRB.getId()) {
                    importance = (Importance.MEDIUM);
                }
                if (checkedId == highImportanceRB.getId()) {
                    importance = (Importance.HIGH);
                }
                if (checkedId == lifeAndDeathImportanceRB.getId()) {
                    importance = (Importance.LIFE_AND_DEATH);
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
        notesSource = new MyDataBaseFirebaseImpl();
    }

    private Note collectNote() {
        String name = this.name.getText().toString();
        String description = this.description.getText().toString();
        Importance importance = this.importance;
        if (currentNote != null) {
            currentNote.setName(name);
            currentNote.setDescription(description);
            currentNote.setImportance(importance);
        }
        return currentNote;
    }
    @Override
    public void onMyClick(View view, int position) {

    }

    protected void showDatePicker() {
        setDate(getView());

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifyTask(currentNote);
    }
}
