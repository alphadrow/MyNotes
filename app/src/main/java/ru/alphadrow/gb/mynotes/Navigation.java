package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {
    private final FragmentManager fragmentManager;
    private boolean isLandscape;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.isLandscape = isLandscape;
    }

    public void addFragment(Fragment fragment, boolean useBackstack, String name) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notesContainer, fragment);
        if (useBackstack) {
            fragmentTransaction.addToBackStack(name);
        }
        fragmentTransaction.commit();
    }

    public void refreshFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    }
}
