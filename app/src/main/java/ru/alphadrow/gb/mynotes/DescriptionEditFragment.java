package ru.alphadrow.gb.mynotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

import ru.alphadrow.gb.mynotes.observe.Publisher;

public class DescriptionEditFragment extends DialogFragment {
    private EditText editDescription;
    private Button buttonChangeDescription;
    Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_description_edit, container, false);
        initView(view);
        initListeners();
        return view;
    }

    private void initListeners() {
        buttonChangeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = editDescription.getText().toString();
                Note note = new Note("", text, new Date(0), Importance.FORGET_ABOUT_IT);
                publisher.notifyTask(note);
                dismiss();
            }
        });
    }

    private void initView(View view) {

        editDescription = view.findViewById(R.id.edit_description);
        buttonChangeDescription = view.findViewById(R.id.button_change_description);
    }

}
