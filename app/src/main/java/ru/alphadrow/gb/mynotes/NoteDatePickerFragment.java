package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;

public class NoteDatePickerFragment extends Fragment {

    DatePicker datePicker;
    Note currentNote;
    boolean isLandScape;
    MyDataBase myDataBase = MyDataBase.getInstance();

    public static NoteDatePickerFragment newInstance(Note note){
        NoteDatePickerFragment fragment=  new NoteDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Settings.ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null) {
            this.currentNote = savedInstanceState.getParcelable(Settings.KEY_NOTE);
            datePicker.updateDate(currentNote.getDateOfCreation().getYear(),
                    currentNote.getDateOfCreation().getMonth(),
                    currentNote.getDateOfCreation().getDay());
        }
        isLandScape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
        if(getArguments()!=null){
            this.currentNote = getArguments().getParcelable(Settings.ARG_NOTE);
        }
    }


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Settings.KEY_NOTE, currentNote);
        outState.putParcelable(Settings.KEY_DB, myDataBase);
        super.onSaveInstanceState(outState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datepicker,container,false);
        datePicker = view.findViewById(R.id.datePicker);
        Button buttonChangeDate = view.findViewById(R.id.button_change_date);
        buttonChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    currentNote.setDateOfCreation(new Date(
                            datePicker.getYear(),
                            datePicker.getMonth() + 1,
                            datePicker.getDayOfMonth()));
                requireActivity().getOnBackPressedDispatcher().onBackPressed();

            }
        });

        return view;
    }

}
