package ru.alphadrow.gb.mynotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class NotePropertiesFragment extends Fragment implements MyOnClickListener {

    public static String ARG_NOTE = "note";
    Note currentNote;
    boolean isLandScape;

    public static NotePropertiesFragment newInstance(Note note) {
        NotePropertiesFragment fragment = new NotePropertiesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (getArguments() != null) {
            this.currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_card_view, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        NotesPropertiesAdapter adapter = new NotesPropertiesAdapter(MyDataBase.getInstance());
        adapter.setOnMyOnClickListener(this);
        recyclerView.setAdapter(adapter);


//        ListView listView = view.findViewById(R.id.)
//        NotesPropertiesAdapter adapter = new NotesPropertiesAdapter(MyDataBase.getInstance());
//        TextView textView = view.findViewById(R.id.name);
//        TextView description = view.findViewById(R.id.description);
//        TextView dateOfCreate = view.findViewById(R.id.dateOfCreation);
//        dateOfCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePicker(currentNote);
//            }
//        });
//        TextView importance = view.findViewById(R.id.importance);
//        textView.setText(this.currentNote.getName());
//        description.setText(this.currentNote.getDescription());
//        dateOfCreate.setText(this.currentNote.getDateOfCreation().toString());
//        importance.setText(this.currentNote.getImportance().toString());

        return view;
    }

    private void showDatePicker() {

        if (isLandScape) {
            showDatePickerLand(currentNote);
        } else {
            showDatePickerPort(currentNote);
        }
    }

    private void showDatePickerPort(Note note) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notesContainer, NoteDatePickerFragment.newInstance(note))
                .addToBackStack("")
                .commit();
    }

    private void showDatePickerLand(Note note) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notePropertiesContainer, NoteDatePickerFragment.newInstance(note))
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onMyClick(View view, int position) {
        showDatePicker();
    }
}
