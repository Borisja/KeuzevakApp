package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import models.SchoolClass;

public class SchoolClassList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SchoolClassListAdapter adapter;
    private List<SchoolClass> classList;
    private List<SchoolClass> userClassList;
    private DatabaseReference firebaseRef;
    private String userUid;

    Button mClassListYear1;
    Button mClassListYear2;
    Button mClassListYear3;
    Button mClassListYear4;
    Button mClassListOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        setUpUiElements();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            userUid = "KM9TfEOWEtRu9dwtdUpnc1cN2C93";
        } else {
            userUid = user.getUid().toString();
        }

        resetLists();

        getUserClassDataFromBackend(1, true);

        mClassListYear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserClassDataFromBackend(1, true);
                setNewAdapter();
            }
        });

        mClassListYear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserClassDataFromBackend(2, true);
                setNewAdapter();
            }
        });

        mClassListYear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserClassDataFromBackend(3, true);
                setNewAdapter();
            }
        });

        mClassListYear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserClassDataFromBackend(4, true);
                setNewAdapter();
            }
        });

        mClassListOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserClassDataFromBackend(5, false);
                setNewAdapter();
            }
        });


        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new SchoolClassListAdapter(this, classList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    private void resetLists() {
        userClassList = new ArrayList<>();
        classList = new ArrayList<>();
    }

    private void setNewAdapter() {
        adapter.notifyItemRangeRemoved(0, 999);
        adapter = new SchoolClassListAdapter(SchoolClassList.this, classList);
        recyclerView.setAdapter(adapter);
    }

    private void setUpUiElements() {
        recyclerView = findViewById(R.id.classListRecyclerView);
        mClassListYear1 = findViewById(R.id.classListYear1);
        mClassListYear2 = findViewById(R.id.classListYear2);
        mClassListYear3 = findViewById(R.id.classListYear3);
        mClassListYear4 = findViewById(R.id.classListYear4);
        mClassListOther = findViewById(R.id.classListYearOther);
    }

    private void getUserClassDataFromBackend(int year, boolean madantory) {
        resetLists();
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userUid).child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()) {

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);

                    userClassList.add(schoolClass);

                }
                getClassDataFromBackend(year, madantory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getClassDataFromBackend(int year, boolean mandatory) {
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()) {

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);

                    if (mandatory) {
                        if (schoolClass.getYear() == year && schoolClass.isMandatory() == mandatory) {
                            for (int i = 0; i < userClassList.size(); i++) {
                                if (userClassList.get(i).getCode().equalsIgnoreCase(schoolClass.getCode())) {
                                    schoolClass = userClassList.get(i);
                                }
                            }

                            classList.add(schoolClass);
                        }

                    } else {
                        if(schoolClass.isMandatory() == mandatory){
                            for (int i = 0; i < userClassList.size(); i++) {
                                if (userClassList.get(i).getCode().equalsIgnoreCase(schoolClass.getCode())) {
                                    schoolClass = userClassList.get(i);
                                }
                            }

                            classList.add(schoolClass);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}