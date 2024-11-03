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

public class NoteFragment extends Fragment implements MyOnClickListener, FragmentForContextMenuRegistrar {
    public static String ARG_NOTE = "note";

    Note currentNote;

    boolean isLandScape;


    NotesSource noteSource = MyDataBaseFirebaseImpl.getInstance();
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Settings.KEY_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        noteAdapter = NoteAdapter.getInstance(this);

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
                navigation.addFragment(NotePropertiesEditFragment.newInstance(), true, "notePropertiesEditFragment_float"); //TODO исправить название метода
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

    @Override
    public void onMyClick(View view, int position) {
        currentNote = noteSource.getNote(position);
        publisher.subscribe(new Observer() {
            @Override
            public void updateState(Note note) {
                noteSource.updateNote(position, currentNote);  // может эти 2 строчки
                noteAdapter.updateNote(position, currentNote); // вынести в отдельный метод? и в других местах...
            }


        });
        navigation.addFragment(NotePropertiesFragment.newInstance(noteSource.getNote(position)), true, "notePropertiesFragment_onClick");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int position = noteAdapter.getMenuContextClickPosition();
        if (item.getItemId() == R.id.deleteItem) {
            noteSource.deleteNote(position);
            noteAdapter.removeItemById(position);
        }
        if (item.getItemId() == R.id.editItem) {
            publisher.subscribe(new Observer() {

                @Override
                public void updateState(Note note) {
                    noteAdapter.updateNote(position, note);
                    noteSource.updateNote(position, note);
                }

            });
            navigation.addFragment(NotePropertiesEditFragment.newInstance(noteSource.getNote(position)), true, "notePropertiesFragment_onOptions");
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = noteAdapter.getMenuContextClickPosition();
        currentNote = noteSource.getNote(position);
        if (item.getItemId() == R.id.deleteItem) {
            Bundle bundle = new Bundle();
            bundle.putInt(Settings.KEY_POS, position);
            DialogBeforeDeleteFragment dialogBeforeDeleteFragment = new DialogBeforeDeleteFragment(bundle);
            dialogBeforeDeleteFragment.show(requireActivity().getSupportFragmentManager(), "TAG");

        }
        if (item.getItemId() == R.id.editItem) {
            publisher.subscribe(new Observer() {

                @Override
                public void updateState(Note note) {
                    noteAdapter.updateNote(position, note);
                    noteSource.updateNote(position, note);
                }
            });
            navigation.addFragment(NotePropertiesEditFragment.newInstance(noteSource.getNote(position)), true, "notePropertiesFragment_onContext");
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

    @Override
    public void register(View view) {
        registerForContextMenu(view);
    }
}
