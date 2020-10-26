package com.example.keuzevakapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import maes.tech.intentanim.CustomIntent;

public class Dashboard extends AppCompatActivity {

    CardView mNewClass;
    CardView mViewClass;
    CardView mClassDataView;
    CardView mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpUIElements();

        mNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), AddSchoolClass.class));

                Intent intent = new Intent(getApplicationContext(), AddSchoolClass.class);
                startActivity(intent);
                CustomIntent.customType(Dashboard.this, "right-to-left");
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user == null){
                    startActivity(new Intent(Dashboard.this, MainActivity.class));
                    finish();
                }
            }
        });

        mViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, SchoolClassList.class));
               //CustomIntent.customType(Dashboard.this, "left-to-right");
            }
        });

        mClassDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SchoolClassDataView.class));
                CustomIntent.customType(Dashboard.this, "bottom-to-up");
            }
        });
    }


    public void setUpUIElements(){
        mNewClass = findViewById(R.id.cardViewNewClass);
        mViewClass = findViewById(R.id.cardViewViewClass);
        mClassDataView = findViewById(R.id.cardViewViewClassData);
        mLogout = findViewById(R.id.cardViewViewLogout);
    }
}