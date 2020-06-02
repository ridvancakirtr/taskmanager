package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    Context context;
    ArrayList<Data> taskModel;

    public TaskAdapter(Context context, ArrayList<Data> taskModel) {
        this.context=context;
        this.taskModel=taskModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(taskModel.get(position).getTitle());
        holder.description.setText(taskModel.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,taskModel.get(position).getTitle(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, TaskDetail.class);
                intent.putExtra("ID", taskModel.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title=itemView.findViewById(R.id.title);
            this.description=itemView.findViewById(R.id.description);
        }
    }
}
