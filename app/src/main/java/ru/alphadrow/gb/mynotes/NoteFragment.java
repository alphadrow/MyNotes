package ru.alphadrow.gb.mynotes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ru.alphadrow.gb.mynotes.observe.Observer;
import ru.alphadrow.gb.mynotes.observe.Publisher;

public class NoteFragment extends Fragment implements MyOnClickListener {

    Note currentNote;

    boolean isLandScape;


    NotesSource noteSource = new MyDataBaseFirebaseImpl();
    NoteAdapter noteAdapter;
    Navigation navigation;
    Publisher publisher;


    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.item_menu, menu);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(Settings.KEY_NOTE);
        }
        if (isLandScape)
            if (currentNote != null) {
                showNoteProperties(currentNote);
            } else {
                showNoteProperties(noteSource.getNote(0));
            }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Settings.KEY_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        noteAdapter = new NoteAdapter(this);

        noteSource.init(new NotesSourceResponse() {

            @Override
            public void initialazed(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        noteAdapter.setOnMyOnClickListener(this);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingButtonPlus);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation.addFragment(NotePropertiesFragmentEdit.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        noteSource.addNote(note);
                        noteAdapter.addNote(note);
                        noteAdapter.notifyItemInserted(noteAdapter.sizeOfList());
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        recyclerView.setAdapter(noteAdapter);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);
    }

    private void showNoteProperties(Note note) {
        currentNote = note;
        if (isLandScape) {
            showNotePropertiesLand();
        } else {
            showNotePropertiesPort();
        }
    }

    private void showNotePropertiesPort() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notesContainer, NotePropertiesFragment.newInstance(currentNote))
                .addToBackStack("")
                .commit();
    }

    private void showNotePropertiesLand() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notePropertiesContainer, NotePropertiesFragment.newInstance(currentNote))
                .commit();
    }

    @Override
    public void onMyClick(View view, int position) {
        showNoteProperties(noteSource.getNote(position));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int position = noteAdapter.getMenuContextClickPosition();
        if (item.getItemId() == R.id.deleteItem) {
            noteSource.deleteNote(position);
            noteAdapter.removeItemById(position);
            noteAdapter.notifyItemRemoved(item.getItemId());
        }
        if (item.getItemId() == R.id.editItem) {
            navigation.addFragment(NotePropertiesFragmentEdit.newInstance(noteSource.getNote(position)), true);
            publisher.subscribe(new Observer() {
                @Override
                public void updateState(Note note) {
                    noteSource.updateNote(position, note);  // может эти 2 строчки
                    noteAdapter.updateNote(position, note); // вынести в отдельный метод? и в других местах...
                    noteAdapter.notifyItemChanged(position);
                }
            });
        }
        return super.onOptionsItemSelected(item);

    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = noteAdapter.getMenuContextClickPosition();
        currentNote = noteSource.getNote(position);
        if (item.getItemId() == R.id.deleteItem) {
            noteSource.deleteNote(position);        //TODO интуитивно понимаю что можно или noteSource спрятать в noteAdapter или наоборот... а как будет правильно?
            noteAdapter.removeItemById(position);
            noteAdapter.notifyItemRemoved(position);
        }
        if (item.getItemId() == R.id.editItem) {
            navigation.addFragment(NotePropertiesFragmentEdit.newInstance(noteSource.getNote(position)), true);
            publisher.subscribe(new Observer() {
                @Override
                public void updateState(Note note) {
                    noteSource.updateNote(position, note);
                    noteAdapter.updateNote(position, note);
                    noteAdapter.notifyItemChanged(noteSource.size() - 1);
                }
            });
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.item_menu, menu);
    }
}
