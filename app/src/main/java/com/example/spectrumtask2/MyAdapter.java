package com.example.spectrumtask2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private final Context context;
    private final ArrayList<TaskList> taskList;

    public MyAdapter(Context context, ArrayList<TaskList> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.show_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvTask.setText (TaskList.getTask());


    }



    @Override
    public int getItemCount() {
        return  taskList == null ? 0 :taskList.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTask;
        ImageView edit,delete;
        RelativeLayout relative;

        public MyViewHolder(@NonNull View itemView) {
            super (itemView);
            tvTask=itemView.findViewById (R.id.showTask);
            edit=(ImageView)itemView.findViewById(R.id.done);
            delete=(ImageView)itemView.findViewById(R.id.cross);
            relative=(itemView).findViewById (R.id.relative);

        }
    }
}
