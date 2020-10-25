package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.SchoolClass;

public class SchoolClassInfo extends AppCompatActivity {

    private TextView mClassCode;
    private TextView mClassName;
    private TextView mClassDescription;
    private TextView mClassEc;
    private TextView mClassYear;
    private TextView mClassPeriod;

    private SharedPreferences sharedpreff;

    private DatabaseReference firebaseRef;

    private SchoolClass schoolClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_class_info);

        setUIElements();

        sharedpreff = getApplicationContext().getSharedPreferences("classCode", Context.MODE_PRIVATE);
        String classCode = sharedpreff.getString("classCode", "");

        getDataFromBackend(classCode);
    }

    private void setUIElements(){
        mClassCode = findViewById(R.id.classCodeInfo);
        mClassName = findViewById(R.id.classNameInfo);
        mClassDescription = findViewById(R.id.classDescriptionInfo);
        mClassEc = findViewById(R.id.classEcInfo);
        mClassYear = findViewById(R.id.classYearInfo);
        mClassPeriod = findViewById(R.id.classPeriodInfo);
    }

    private void getDataFromBackend(String selectedClass){
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("classes").child(selectedClass);
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                schoolClass = snapshot.getValue(SchoolClass.class);

                fillActivityWithData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fillActivityWithData(){
        mClassCode.setText(schoolClass.getCode());
        mClassName.setText(schoolClass.getName());
        mClassDescription.setText(schoolClass.getDescription());
        mClassEc.setText("EC: " + schoolClass.getEc());
        mClassYear.setText("Year: " + schoolClass.getYear());
        mClassPeriod.setText("Period: " + schoolClass.getPeriod());
    }
}