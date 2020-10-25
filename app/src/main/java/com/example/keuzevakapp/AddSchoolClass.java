package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.SchoolClass;

public class AddSchoolClass extends AppCompatActivity {

    EditText mCode;
    EditText mName;
    EditText mDescription;
    EditText mEc;
    EditText mYear;
    EditText mPeriod;
    Switch mNonMandatory;

    DatabaseReference mDatabase;

    Button mCreateClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        setupUIElements();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchoolClass newClass = new SchoolClass(
                        mCode.getText().toString().toUpperCase(),
                        mName.getText().toString(),
                        mDescription.getText().toString(),
                        null,
                        Integer.parseInt(mEc.getText().toString()),
                        Integer.parseInt(mYear.getText().toString()),
                        Integer.parseInt(mPeriod.getText().toString()),
                        0,
                        true);


                mDatabase.keepSynced(true);

                mDatabase.child("classes").child(newClass.getCode()).setValue(newClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            if(mNonMandatory.isChecked()){
                                newClass.setMandatory(false);
                            }
                            
                            Toast.makeText(AddSchoolClass.this, "Class " + newClass.getCode() + " has been created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddSchoolClass.this, getString(R.string.defaultError), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


    public void setupUIElements(){
        mCode = findViewById(R.id.textClassCode);
        mName = findViewById(R.id.textClassName);
        mDescription = findViewById(R.id.textClassDescription);
        mEc = findViewById(R.id.textClassEC);
        mYear = findViewById(R.id.textCLassYear);
        mPeriod = findViewById(R.id.textClassPeriode);
        mNonMandatory = findViewById(R.id.switchNonMandatory);

        mCreateClass = findViewById(R.id.createCLassBtn);
    }
}