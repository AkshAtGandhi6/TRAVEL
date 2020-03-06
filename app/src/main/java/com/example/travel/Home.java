package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

   FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
Button button;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         button =  findViewById(R.id.signout);
        mAuth = FirebaseAuth.getInstance();

mAuthListner=new FirebaseAuth.AuthStateListener() {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(Home.this, MainActivity.class));
        }
    }
};

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mAuth.signOut();

    }
});

    }
}
