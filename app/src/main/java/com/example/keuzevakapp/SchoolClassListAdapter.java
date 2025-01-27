package com.example.keuzevakapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import maes.tech.intentanim.CustomIntent;
import models.SchoolClass;

public class SchoolClassListAdapter extends RecyclerView.Adapter<SchoolClassListAdapter.ViewHolder> {

    private Context context;
    private List<SchoolClass> classList;
    private SharedPreferences sharedPref;

    public SchoolClassListAdapter(Context context, List<SchoolClass> classList) {
        this.context = context;
        this.classList = classList;
    }

    public void updateDataset(List<SchoolClass> classList) {
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

        if(schoolClass.getNotes() != null){
            holder.classNotes.setVisibility(View.VISIBLE);
        }

        if(schoolClass.getGrade() >= 5.5){
            holder.classPassed.setVisibility(View.VISIBLE);
        }

        if(!schoolClass.isMandatory()){
            holder.classCardView.setCardBackgroundColor(Color.LTGRAY);
        }

        holder.itemView.setOnClickListener((view) -> {

            sharedPref = context.getSharedPreferences("classCode", context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("classCode", schoolClass.getCode());
            editor.commit();

            Intent itent = new Intent(context, SchoolClassInfo.class);

            context.startActivity(itent);
            CustomIntent.customType(context, "fadein-to-fadeout");


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
        public ImageView classNotes;
        public ImageView classPassed;
        public CardView classCardView;


        public ViewHolder(View itemView) {
            super(itemView);

            classTitle = itemView.findViewById(R.id.classTitle);
            className = itemView.findViewById(R.id.className);
            classEC = itemView.findViewById(R.id.classEC);
            classYear = itemView.findViewById(R.id.classYear);
            classPeriod = itemView.findViewById(R.id.classPeriod);
            classNotes = itemView.findViewById(R.id.classHasNotes);
            classPassed = itemView.findViewById(R.id.classPassed);
            classCardView = itemView.findViewById(R.id.classCardView);


        }
    }
}
