package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import models.SchoolClass;

public class ClassList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClassListAdapter adapter;
    private List<SchoolClass> classList;
    private DatabaseReference firebaseRef;
    private JSONArray classesJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        classList = new ArrayList<>();

        getDataFromBackend();

        recyclerView = (RecyclerView) findViewById(R.id.classListRecyclerView);

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ClassListAdapter(this, classList);
        recyclerView.setAdapter(adapter);
    }


    private void getDataFromBackend(){
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("classes");
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()){

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);

                   classList.add(schoolClass);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}