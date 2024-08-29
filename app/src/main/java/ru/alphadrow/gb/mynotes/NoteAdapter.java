package ru.alphadrow.gb.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    MyDataBase myDataBase = MyDataBase.getInstance();
    private MyOnClickListener listener;
    private MyOnLongClickListener longClickListener;

    private int menuContextClickPosition;

    private Fragment fragment;
    public void setOnMyOnClickListener(MyOnClickListener listener){
        this.listener = listener;
    }
    public void setOnMyOnLongClickListener(MyOnLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    public NoteAdapter(Fragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setName(myDataBase.getNote(position).getName());
        holder.setDateOfCreation(myDataBase.getNote(position).getDateOfCreation().toString());
    }



    @Override
    public int getItemCount() {
        return myDataBase.getNoteList().size();
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
            fragment.registerForContextMenu(cardView);
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
