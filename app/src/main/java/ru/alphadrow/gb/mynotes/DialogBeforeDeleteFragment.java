package ru.alphadrow.gb.mynotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class DialogBeforeDeleteFragment extends DialogFragment {
    int position;
    NoteAdapter noteAdapter;
    NotesSource noteSource;

    public DialogBeforeDeleteFragment() {

    }

    public DialogBeforeDeleteFragment(Bundle bundle) {
        position = bundle.getInt(Settings.KEY_POS);
        noteSource = MyDataBaseFirebaseImpl.getInstance();
        noteAdapter = NoteAdapter.getInstance(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        noteSource = MyDataBaseFirebaseImpl.getInstance();
        noteAdapter = NoteAdapter.getInstance(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_before_delete, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.delete_warning_message).setView(view).setNegativeButton(R.string.no, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton(R.string.yes, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("myLogs", "position: " + position);
                noteSource.deleteNote(position);
                noteAdapter.removeItemById(position);
            }
        });

        return builder.create();
    }

    @Override
    public void onStop() {
        Log.d("MyLogs:", "onStop");
        super.onStop();
    }
}

