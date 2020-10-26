package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import maes.tech.intentanim.CustomIntent;
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
                if (checkVallues()) {
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


                    if (mNonMandatory.isChecked()) {
                        newClass.setMandatory(false);
                    }


                    mDatabase.keepSynced(true);

                    mDatabase.child("classes").child(newClass.getCode()).setValue(newClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddSchoolClass.this, "Class " + newClass.getCode() + " has been created", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();
                            } else {
                                Toast.makeText(AddSchoolClass.this, getString(R.string.defaultError), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "left-to-right");
    }

    public void setupUIElements() {
        mCode = findViewById(R.id.textClassCode);
        mName = findViewById(R.id.textClassName);
        mDescription = findViewById(R.id.textClassDescription);
        mEc = findViewById(R.id.textClassEC);
        mYear = findViewById(R.id.textCLassYear);
        mPeriod = findViewById(R.id.textClassPeriode);
        mNonMandatory = findViewById(R.id.switchNonMandatory);

        mCreateClass = findViewById(R.id.createCLassBtn);
    }

    public boolean checkVallues() {
        String code = mCode.getText().toString();
        String name = mName.getText().toString();
        String description = mDescription.getText().toString();
        String ec = mEc.getText().toString();
        String year = mYear.getText().toString();
        String period = mPeriod.getText().toString();

        Boolean incomplete = false;

        if (code.isEmpty()) {
            mCode.setError(getString(R.string.textLoginError));
            incomplete = true;
        }

        if (name.isEmpty()) {
            mName.setError(getString(R.string.textLoginError));
            incomplete = true;
        }

        if (description.isEmpty()) {
            mDescription.setError(getString(R.string.textLoginError));
            incomplete = true;
        }

        if (ec.isEmpty()) {
            mEc.setError(getString(R.string.textLoginError));
            incomplete = true;
        }

        if (year.isEmpty()) {
            mYear.setError(getString(R.string.textLoginError));
            incomplete = true;
        }

        if (period.isEmpty()) {
            mPeriod.setError(getString(R.string.textLoginError));
            incomplete = true;
        }

        if (incomplete) {
            return false;
        }
        return true;
    }
}