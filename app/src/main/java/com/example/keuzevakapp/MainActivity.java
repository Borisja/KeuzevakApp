package com.example.keuzevakapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import models.User;

import static android.util.Log.*;

public class MainActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    FirebaseAuth fAuth;

    Button mLoginBtn;
    Button mRegister;
    Button mOffline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setUpUIElements();

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmailAndPassword()){
                    fAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                                User currentUser = new User(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.defaultError), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast t = Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmailAndPassword()){
                    fAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.defaultError), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {

                }
            }
        });

        mOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                Toast.makeText(MainActivity.this, "You are offline!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected void setUpUIElements(){
        mEmail = findViewById(R.id.editTextTextEmailAddress);
        mPassword = findViewById(R.id.editTextTextPassword);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegister = findViewById(R.id.registerBtn);
        mOffline = findViewById(R.id.offlineBtn);
    }

    public boolean checkEmailAndPassword(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        Boolean incomplete = false;

        if(email.isEmpty()) {
            mEmail.setError(getString(R.string.textLoginError));
            incomplete = true;
        }

        if(password.isEmpty()){
            mPassword.setError(getString(R.string.textPasswordError));
            incomplete = true;
        }

        if(incomplete){
            return false;
        }
        return true;
    }
}