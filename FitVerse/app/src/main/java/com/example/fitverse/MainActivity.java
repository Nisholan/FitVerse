package com.example.fitverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    EditText email, password;
    Button login, register, logout;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    CardView loginCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);
        currentUser = mAuth.getCurrentUser();
        loginCardView = findViewById(R.id.login);
        logout = findViewById(R.id.btn_logout);


        if (currentUser!=null)
        {
            loginCardView.setVisibility(View.GONE);


        }
        else
            {
                loginCardView.setVisibility(View.VISIBLE);


            }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = email.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "User" + " " + mAuth.getCurrentUser().getEmail() + " " + "Successfully Registered", Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    Toast.makeText(MainActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e) 
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = email.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(intent);

                mAuth.signInWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())

                        {
                            loginCardView.setVisibility(View.GONE);

                            Toast.makeText(MainActivity.this, "Logged In" + " " + mAuth.getCurrentUser().getEmail() + " " + "Successfully", Toast.LENGTH_SHORT).show();



                        }

                        else
                            {
                                Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();


                            }



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "You Have Logged Out!", Toast.LENGTH_SHORT).show();
                loginCardView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (currentUser!=null)
        {
            Toast.makeText(this, "Already Logged In As" + " " + currentUser.getEmail() + " ", Toast.LENGTH_SHORT).show();

        }
    }




}