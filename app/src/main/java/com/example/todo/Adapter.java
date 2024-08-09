package com.example.todo;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//step 4:create a adapter
public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {

    private List<modelClass> modelClassList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
    }


    public Adapter(List<modelClass> modelClassList, OnItemClickListener onItemClickListener) {
        this.modelClassList = modelClassList;
        this.onItemClickListener= onItemClickListener;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder,
                                 @SuppressLint("RecyclerView") int position) {
//        String task=modelClassList.get(position).getTask();
//        viewholder.setData(task);
        modelClass model = modelClassList.get(position);
        holder.task.setText(model.getTask());
        holder.checkBox.setChecked(false);

        holder.task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.iconEdit.getVisibility() == View.GONE) {
                    holder.iconEdit.setVisibility(View.VISIBLE);
                    holder.iconDelete.setVisibility(View.VISIBLE);
                } else {
                    holder.iconEdit.setVisibility(View.GONE);
                    holder.iconDelete.setVisibility(View.GONE);
                }
            }
        });

        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onEditClick(position);
            }
        });


        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }


    class Viewholder extends RecyclerView.ViewHolder{

        ImageView iconEdit;
        ImageView iconDelete;
        private TextView task;
        private CheckBox checkBox;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            task=itemView.findViewById(R.id.task);
            checkBox=itemView.findViewById(R.id.checkBox);
            iconEdit=itemView.findViewById(R.id.editIcon);
            iconDelete=itemView.findViewById(R.id.deleteIcon);
        }

//        private void setData(String taskText){
//            task.setText(taskText);
//
//        }
    }
}

