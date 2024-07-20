package ru.alphadrow.gb.mynotes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {
    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, boolean useBackstack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notesContainer, fragment);
        if(useBackstack){
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction.commit();
    }
}
