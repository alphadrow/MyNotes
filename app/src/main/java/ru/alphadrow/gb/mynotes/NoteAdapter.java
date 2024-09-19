package ru.alphadrow.gb.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {


    public  int sizeOfList() {
        return notes.size();
    }

    public void setNotes(List<Note> notesList) {
        this.notes = notesList;
        notifyDataSetChanged();
    }


    private List<Note> notes = new ArrayList<>();

    private MyOnClickListener listener;

    private int menuContextClickPosition;

    private FragmentForContextMenuRegistrar registrar;
    public void setOnMyOnClickListener(MyOnClickListener listener){
        this.listener = listener;
    }


    public NoteAdapter(FragmentForContextMenuRegistrar registrar) {
        this.registrar = registrar;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new MyViewHolder(view);
    }

    public void removeItemById(int position){
        notes.remove(position);
    }

    public void addNote(Note note){
        notes.add(note);
    }


    public void updateNote(int position, Note note){
        notes.set(position, note);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setName(notes.get(position).getName());
        holder.setDateOfCreation(notes.get(position).getDateOfCreation().toString());
    }



    @Override
    public int getItemCount() {
        return notes.size();
    }

    public int getMenuContextClickPosition() {
        return menuContextClickPosition;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public void setName(String name) {
            this.name.setText(name);
        }

        public void setDateOfCreation(String dateOfCreation) {
            this.dateOfCreation.setText(dateOfCreation);
        }

        private TextView name;
        private TextView dateOfCreation;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.noteCardView);
            name = itemView.findViewById(R.id.noteNameTextView);
            dateOfCreation = itemView.findViewById(R.id.noteCreateAtTextView);
            registrar.register(cardView);
            cardView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    menuContextClickPosition = getAdapterPosition();
                    return false;
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyClick(v, getAdapterPosition());
                }
            });
        }
    }
}
