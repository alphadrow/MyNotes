package ru.alphadrow.gb.mynotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

import ru.alphadrow.gb.mynotes.observe.Publisher;

public class ImportanceEditFragment extends DialogFragment {
    private Button buttonChangeImportance;
    RadioGroup radioGroup;
    RadioButton forgetAboutItImportance;
    RadioButton lowImportance;
    RadioButton mediumImportance;
    RadioButton highImportance;
    RadioButton lifeAndDeathImportance;
    Importance importance;
    Publisher publisher;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = MyApp.getPublisher();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_importance_edit, container, false);
        initView(view);
        initListeners();
        return view;
    }

    private void initListeners() {
        buttonChangeImportance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note("", "", new Date(0), importance);
                publisher.notifyTask(note);
                dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.lifeAndDeathImportance) {
                    importance = Importance.LIFE_AND_DEATH;
                }
                if (checkedId == R.id.lowImportance) {
                    importance = Importance.LOW;
                }
                if (checkedId == R.id.mediumImportance) {
                    importance = Importance.MEDIUM;
                }
                if (checkedId == R.id.highImportance) {
                    importance = Importance.HIGH;
                }
                if (checkedId == R.id.forgetAboutItImportance) {
                    importance = Importance.FORGET_ABOUT_IT;
                }
            }
        });
    }


    private void initView(View view) {
        radioGroup = view.findViewById(R.id.importance);
        forgetAboutItImportance = view.findViewById(R.id.forgetAboutItImportance);
        lowImportance = view.findViewById(R.id.lowImportance);
        mediumImportance = view.findViewById(R.id.mediumImportance);
        highImportance = view.findViewById(R.id.highImportance);
        lifeAndDeathImportance = view.findViewById(R.id.lifeAndDeathImportance);
        buttonChangeImportance = view.findViewById(R.id.button_change_importance);
    }

}

