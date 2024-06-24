package ru.alphadrow.gb.mynotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    MyDataBase myDataBase = MyDataBase.getInstance();
    private String[] dataSource;
    private MyOnClickListener listener;
    public void setOnMyOnClickListener(MyOnClickListener listener){
        this.listener = listener;
    }

    public NotesAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    public void setData(String[] dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(dataSource[position]);
        Log.d("aa","aa");
    }



    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.noteNameTv);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyClick(v, getAdapterPosition());
                }
            });
        }
        public void onBind(String noteName) {
            name.setText(noteName);
        }
    }
}
