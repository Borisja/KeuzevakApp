package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import maes.tech.intentanim.CustomIntent;
import models.SchoolClass;
import models.User;

public class SchoolClassInfo extends AppCompatActivity {

    private TextView mClassCode;
    private TextView mClassName;
    private TextView mClassDescription;
    private TextView mClassEc;
    private TextView mClassYear;
    private TextView mClassPeriod;
    private EditText mClassNotes;
    private EditText mClassGrade;
    private Button mSaveBtn;

    private SharedPreferences sharedPreff;

    private DatabaseReference firebaseRef;
    private FirebaseAuth fAuth;

    private SchoolClass schoolClass;
    private String userUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_class_info);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            userUid = "KM9TfEOWEtRu9dwtdUpnc1cN2C93";
        } else {
            userUid = user.getUid().toString();
        }

        setupUIElements();

        sharedPreff = getApplicationContext().getSharedPreferences("classCode", Context.MODE_PRIVATE);
        String classCode = sharedPreff.getString("classCode", "");

        getDataFromBackend(classCode);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!setValuesToModel()){
                    return;
                }

                firebaseRef.keepSynced(true);

                firebaseRef.getRoot().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("classes").child(schoolClass.getCode()).setValue(schoolClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SchoolClassInfo.this, "Class " + schoolClass.getCode() + " has been saved!", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(), SchoolClassList.class));
                            finish();
                        } else {
                            Toast.makeText(SchoolClassInfo.this, getString(R.string.defaultError), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

    private void setupUIElements(){
        mClassCode = findViewById(R.id.classCodeInfo);
        mClassName = findViewById(R.id.classNameInfo);
        mClassDescription = findViewById(R.id.classDescriptionInfo);
        mClassEc = findViewById(R.id.classEcInfo);
        mClassYear = findViewById(R.id.classYearInfo);
        mClassPeriod = findViewById(R.id.classPeriodInfo);
        mClassNotes = findViewById(R.id.classNotesInfo);
        mClassGrade = findViewById(R.id.classGradeInfo);

        mSaveBtn = findViewById(R.id.saveClassBtn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            mSaveBtn.setEnabled(false);
        }
    }

    private void getDataFromBackend(String selectedClass){
        firebaseRef = FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(userUid).child("classes").child(selectedClass);
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    schoolClass = snapshot.getValue(SchoolClass.class);

                    fillActivityWithData();
                } else {
                    getClassTemplateFromBackend(selectedClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SchoolClassInfo.this, getString(R.string.defaultError), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getClassTemplateFromBackend(String selectedClass){
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
        mClassNotes.setText(schoolClass.getNotes());
        mClassGrade.setText(String.valueOf(schoolClass.getGrade()));
    }

    private boolean setValuesToModel(){
        if(mClassNotes.getText().toString().isEmpty()){
            schoolClass.setNotes(null);
        } else {
            schoolClass.setNotes(mClassNotes.getText().toString());
        }


        if(!mClassGrade.getText().toString().isEmpty()){
            if(Double.parseDouble(mClassGrade.getText().toString()) >= 1 && Double.parseDouble(mClassGrade.getText().toString()) <= 10){
                schoolClass.setGrade(Float.parseFloat(mClassGrade.getText().toString()));
            } else {
                Toast.makeText(this, "The grade must either 1 or 10 or between 1 and 10!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}