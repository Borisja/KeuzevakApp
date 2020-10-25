package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import models.SchoolClass;

public class SchoolClassDataView extends AppCompatActivity {

    PieChart piechart;
    DatabaseReference firebaseRef;
    SchoolClass schoolClass;

    ArrayList<PieEntry> values;

    int ECMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_class_data_view);

        setUpUiElements();
        getClassTemplateFromBackend();
    }

    private void setUpUiElements(){
        piechart = findViewById(R.id.piechart);

        values = new ArrayList<>();
    }

    private  void setUpPieChart(){
        piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);
        piechart.setExtraOffsets(5, 10, 5, 5);

        piechart.setDragDecelerationFrictionCoef(0.15f);

        piechart.setDrawHoleEnabled(false);
        piechart.setHoleColor(Color.WHITE);
        piechart.setTransparentCircleRadius(61f);

        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        piechart.setData(data);
    }

    private void getUserClassFromBackend(){
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()){

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);

                    if(schoolClass.getGrade() >= 5.5){
                        ECMax = ECMax - schoolClass.getEc();
                        values.add(new PieEntry(schoolClass.getEc(),  schoolClass.getCode() + ": " + schoolClass.getEc() + " EC"));
                    }

                }

                values.add(new PieEntry(ECMax, "Unclaimed EC: " + ECMax));
                setUpPieChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getClassTemplateFromBackend(){
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()){

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);
                    ECMax = ECMax + schoolClass.getEc();

                }
                getUserClassFromBackend();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
