package ru.alphadrow.gb.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesPropertiesAdapter extends RecyclerView.Adapter<NotesPropertiesAdapter.MyViewHolder> {

    private MyDataBase myDataBase;
    private MyOnClickListener listener;

    public NotesPropertiesAdapter(MyDataBase myDataBase) {
        this.myDataBase = myDataBase;
    }

    public void setOnMyOnClickListener(MyOnClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public NotesPropertiesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_note_properties, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesPropertiesAdapter.MyViewHolder holder, int position) {
        holder.name.setText(myDataBase.getNote(position).getName());
        holder.description.setText(myDataBase.getNote(position).getDescription());
        holder.dateOfCreation.setText(myDataBase.getNote(position).getDateOfCreation().toString());
        holder.importance.setText(myDataBase.getNote(position).getImportance().toString());
    }

    @Override
    public int getItemCount() {
        return myDataBase.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView dateOfCreation;
        TextView importance;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            dateOfCreation = itemView.findViewById(R.id.timeAndDateTextView);
            importance = itemView.findViewById(R.id.importanceTextView);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyClick(v, getAdapterPosition());
                }
            });
        }
    }
}
