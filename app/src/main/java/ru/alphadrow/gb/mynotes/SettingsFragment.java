package ru.alphadrow.gb.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    RadioButton radioButtonModeOne;
    RadioButton radioButtonModeTwo;

    MyDataBase myDataBase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myDataBase = MyDataBase.getInstance();
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        setContent(v);
        setListeners();
        return v;
    }

    private void setContent(View v) {
        radioButtonModeOne = v.findViewById(R.id.buttonModeOne);
        radioButtonModeTwo = v.findViewById(R.id.buttonModeTwo);
    }

    private void setListeners() {
        radioButtonModeOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isModeOneSelected = true;
                Settings.isModeTwoSelected = false;
                Toast toast = new Toast(requireContext());
                toast.setText("mode one selected");
                toast.show();
            }
        });

        radioButtonModeTwo.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.isModeOneSelected = false;
                Settings.isModeTwoSelected = true;
                Toast toast = new Toast(requireContext());
                toast.setText("mode two selected");
                toast.show();
            }
        }));
    }

}