package com.example.keuzevakapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import models.SchoolClass;

public class SchoolClassListAdapter extends RecyclerView.Adapter<SchoolClassListAdapter.ViewHolder> {

    private Context context;
    private List<SchoolClass> classList;
    private SharedPreferences sharedPref;

    public SchoolClassListAdapter(Context context, List<SchoolClass> classList) {
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
        holder.className.setText(schoolClass.getName());
        holder.classEC.setText("EC: " + schoolClass.getEc());
        holder.classYear.setText("Year: " + schoolClass.getYear());
        holder.classPeriod.setText("Periode: "+schoolClass.getPeriod());

        holder.itemView.setOnClickListener((view) -> {

            sharedPref = context.getSharedPreferences("classCode", context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("classCode", schoolClass.getCode());
            editor.commit();

            Intent itent = new Intent(context, SchoolClassInfo.class);

            context.startActivity(itent);
        });

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView classTitle;
        public TextView className;
        public TextView classEC;
        public TextView classYear;
        public TextView classPeriod;


        public ViewHolder(View itemView) {
            super(itemView);

            classTitle = itemView.findViewById(R.id.classTitle);
            className = itemView.findViewById(R.id.className);
            classEC = itemView.findViewById(R.id.classEC);
            classYear = itemView.findViewById(R.id.classYear);
            classPeriod = itemView.findViewById(R.id.classPeriod);
        }
    }
}
