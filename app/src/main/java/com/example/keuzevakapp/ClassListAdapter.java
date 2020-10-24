package com.example.keuzevakapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import models.SchoolClass;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ViewHolder> {

    private Context context;
    private List<SchoolClass> classList;

    public ClassListAdapter(Context context, List<SchoolClass> classList) {
        this.context = context;
        this.classList = classList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_class_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SchoolClass schoolClass = classList.get(position);

        holder.classTitle.setText(schoolClass.getCode());

        //Glide.with(context).load();

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView classTitle;


        public ViewHolder(View itemView) {
            super(itemView);

            classTitle = itemView.findViewById(R.id.classTitle);
        }
    }
}
