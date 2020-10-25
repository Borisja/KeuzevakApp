package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import models.SchoolClass;

public class SchoolClassList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SchoolClassListAdapter adapter;
    private List<SchoolClass> classList;
    private List<SchoolClass> userClassList;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        classList = new ArrayList<>();
        userClassList = new ArrayList<>();

        getUserClassDataFromBackend();
        getClassDataFromBackend();

        recyclerView = (RecyclerView) findViewById(R.id.classListRecyclerView);

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new SchoolClassListAdapter(this, classList);
        recyclerView.setAdapter(adapter);
    }

    private void getUserClassDataFromBackend(){
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()){

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);

                    userClassList.add(schoolClass);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getClassDataFromBackend(){
        firebaseRef = FirebaseDatabase.getInstance().getReference().child("classes");
        firebaseRef.keepSynced(true);
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot classSnapshot : snapshot.getChildren()){

                    SchoolClass schoolClass = classSnapshot.getValue(SchoolClass.class);

                   for(int i = 0; i < userClassList.size(); i++){
                       if(userClassList.get(i).getCode().equalsIgnoreCase(schoolClass.getCode())){
                           schoolClass = userClassList.get(i);
                       }
                   }

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