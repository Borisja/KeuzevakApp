package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;
import models.SchoolClass;

public class SchoolClassDataView extends AppCompatActivity {

    PieChart piechart;
    DatabaseReference firebaseRef;
    SchoolClass schoolClass;
    Button mYear1Btn;
    Button mYear2Btn;
    Button mYear3Btn;
    Button mYear4Btn;
    String userUid;

    ArrayList<PieEntry> values;

    int ECMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_class_data_view);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            userUid = "KM9TfEOWEtRu9dwtdUpnc1cN2C93";
        } else {
            userUid = user.getUid().toString();
        }

        setUpUiElements();
        getClassTemplateFromBackend(1);

        mYear1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPieValues();
                getClassTemplateFromBackend(1);
            }
        });

        mYear2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPieValues();
                getClassTemplateFromBackend(2);
            }
        });

        mYear3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPieValues();
                getClassTemplateFromBackend(3);
            }
        });

        mYear4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPieValues();
                getClassTemplateFromBackend(4);
            }
        });
    }

    private void resetPieValues() {
        ECMax = 0;
        values = new ArrayList<>();
    }

    private void setUpUiElements() {
        piechart = findViewById(R.id.piechart);
        mYear1Btn = findViewById(R.id.year1Btn);
        mYear2Btn = findViewById(R.id.year2Btn);
        mYear3Btn = findViewById(R.id.year3Btn);
        mYear4Btn = findViewById(R.id.year4Btn);

        values = new ArrayList<>();
    }

    private void setUpPieChart() {
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

    private void getUserClassFromBackend(int year) {
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userUid).child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()) {

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);
                    if (schoolClass.getYear() == year) {

                        if (schoolClass.getGrade() >= 5.5) {
                            ECMax = ECMax - schoolClass.getEc();
                            values.add(new PieEntry(schoolClass.getEc(), schoolClass.getCode() + ": " + schoolClass.getEc() + " EC"));
                        }

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

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "up-to-bottom");
    }

    private void getClassTemplateFromBackend(int year) {
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()) {
                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);
                    if (schoolClass.getYear() == year) {
                        ECMax = ECMax + schoolClass.getEc();
                    }
                }
                getUserClassFromBackend(year);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
