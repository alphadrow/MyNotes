package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import java.time.LocalDate;

public class NoteDatePickerFragment extends Fragment {

    DatePicker datePicker;
    Note currentNote;
    boolean isLandScape;
    MyDataBase myDataBase = MyDataBase.getInstance();

    public static NoteDatePickerFragment newInstance(Note note){
        NoteDatePickerFragment fragment=  new NoteDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            this.currentNote = savedInstanceState.getParcelable(Constants.KEY_NOTE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                datePicker.updateDate(currentNote.getDateOfCreation().getYear(),
                        currentNote.getDateOfCreation().getMonthValue(),
                        currentNote.getDateOfCreation().getDayOfMonth());
            }
        }
        isLandScape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
        if(getArguments()!=null){
            this.currentNote = getArguments().getParcelable(Constants.ARG_NOTE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.KEY_NOTE, currentNote);
        outState.putParcelable(Constants.KEY_DB, myDataBase);
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    currentNote.setDateOfCreation(LocalDate.of(
                            datePicker.getYear(),
                            datePicker.getMonth() + 1,
                            datePicker.getDayOfMonth()));
                }
            }
        });
        return view;
    }

}
