package com.example.keuzevakapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    Button mLogOutBtn;
    CardView mNewClass;
    CardView mViewClass;
    CardView mClassDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpUIElements();

        mNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddSchoolClass.class));
            }
        });

//        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
//        });

        mViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SchoolClassList.class));
            }
        });

        mClassDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SchoolClassDataView.class));
            }
        });
    }


    public void setUpUIElements(){
        //mLogOutBtn = findViewById(R.id.logoutBtn);
        mNewClass = findViewById(R.id.cardViewNewClass);
        mViewClass = findViewById(R.id.cardViewViewClass);
        mClassDataView = findViewById(R.id.cardViewViewClassData);
    }
}