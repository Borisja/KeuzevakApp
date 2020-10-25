package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import models.User;

import static android.util.Log.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    EditText mEmail, mPassword;
    FirebaseAuth fAuth;

    Button mLoginBtn;
    Button mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddSchoolClass()).commit();
            navigationView.setCheckedItem(R.id.nav_newClass);
        }
//        setUpUIElements();
//
//        fAuth = FirebaseAuth.getInstance();
//
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), Dashboard.class));
//            finish();
//        }
//
//
//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(checkEmailAndPassword()){
//                    fAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(MainActivity.this, task.getResult().toString(), Toast.LENGTH_SHORT).show();
//                                User currentUser = new User(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
//                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
//                            } else {
//                                Toast.makeText(MainActivity.this, getString(R.string.defaultError) + " " + task.getException(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                } else {
//                    Toast t = Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT);
//                    t.show();
//                }
//            }
//        });

//        mRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(checkEmailAndPassword()){
//                    fAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(MainActivity.this, "User created", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
//                            } else {
//                                Toast.makeText(MainActivity.this, getString(R.string.defaultError) + " " + task.getException(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                } else {
//
//                }
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_classData:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddSchoolClass()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //
//    protected void setUpUIElements(){
//        mEmail = findViewById(R.id.editTextTextEmailAddress);
//        mPassword = findViewById(R.id.editTextTextPassword);
//        mLoginBtn = findViewById(R.id.loginBtn);
//        mRegister = findViewById(R.id.registerBtn);
//    }
//
//    public boolean checkEmailAndPassword(){
//        String email = mEmail.getText().toString();
//        String password = mPassword.getText().toString();
//
//        Boolean incomplete = false;
//
//        if(email.isEmpty()) {
//            mEmail.setError(getString(R.string.textLoginError));
//            incomplete = true;
//        }
//
//        if(password.isEmpty()){
//            mPassword.setError(getString(R.string.textPasswordError));
//            incomplete = true;
//        }
//
//        if(incomplete){
//            return false;
//        }
//        return true;
//    }
}