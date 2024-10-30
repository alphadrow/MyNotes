package ru.alphadrow.gb.mynotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

import ru.alphadrow.gb.mynotes.observe.Publisher;

public class DateOfCreationEditFragment extends DialogFragment {
    private DatePicker datePicker;
    private Button buttonChangeDate;
    Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_datepicker, container, false);
        initView(view);
        initListeners();
        return view;
    }

    private void initListeners() {
        buttonChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = (new Date(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));
                Note note = new Note("", "", date, Importance.FORGET_ABOUT_IT);
                publisher.notifyTask(note);
                dismiss();
            }
        });
    }

    private void initView(View view) {

        datePicker = view.findViewById(R.id.datePicker);
        buttonChangeDate = view.findViewById(R.id.button_change_date);
    }

}
