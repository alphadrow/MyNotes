package ru.alphadrow.gb.mynotes;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import ru.alphadrow.gb.mynotes.observe.Observer;
import ru.alphadrow.gb.mynotes.observe.Publisher;

public class NameEditFragment extends DialogFragment {
    private EditText editName;
    private Button buttonChangeName;
    Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_name_edit, container, false);
        initView(view);
        initListeners();
        return view;
    }

    private void initListeners() {
        buttonChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = editName.getText().toString();
                Note note = new Note(text, "", new Date(0), Importance.FORGET_ABOUT_IT);
                publisher.notifyTask(note);
                dismiss();
            }
        });
    }

    private void initView(View view) {

        editName = view.findViewById(R.id.edit_name);
        buttonChangeName = view.findViewById(R.id.button_change_name);
    }

}
