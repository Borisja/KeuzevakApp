package com.example.keuzevakapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    Button mLogOutBtn;
    Button mAddClassBtn;
    Button mViewClassesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpUIElements();

        mAddClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddClass.class));
                finish();
            }
        });

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        mViewClassesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClassList.class));
                finish();
            }
        });
    }


    public void setUpUIElements(){
        mLogOutBtn = findViewById(R.id.logoutBtn);
        mAddClassBtn = findViewById(R.id.addNewClassBtn);
        mViewClassesBtn = findViewById(R.id.viewClassesBtn);
    }
}