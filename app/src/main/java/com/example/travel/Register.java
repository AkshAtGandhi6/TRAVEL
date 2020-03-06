package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity {
    EditText fullname, mail,password,phone;
    Button signin;
    FloatingActionButton signup;
    FirebaseAuth fauth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_register);

        mail= findViewById(R.id.Email);
        password= findViewById(R.id.Password);
        fullname= findViewById(R.id.Name);
        phone= findViewById(R.id.Mobile_no);
        signup = findViewById(R.id.Signup_btn);
        signin= findViewById(R.id.Signin_btn);

        fauth= FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar);
//        if(fauth.getCurrentUser() !=null)
//        {
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//           finish();
//       }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= mail.getText().toString().trim();
                String pass= password.getText().toString().trim();
                String number= phone.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mail.setError("Email is required");
                    mail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    mail.setError("Email is invalid");
                    mail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(pass))
                {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(number))
                {
                    phone.setError("Phone number is required");
                    phone.requestFocus();
                    return;
                }
                if(number.length()!=10)
                {
                    phone.setError("Invalid phone number");
                    phone.requestFocus();
                    return;
                }


                if(password.length()<6)
                {
                    password.setError("Password must be greater than 6 characters");
                    password.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Register.this, "You are already registered", Toast.LENGTH_SHORT);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT);
                            }
                        }
                    }
                });
            }


        });
    }
}


