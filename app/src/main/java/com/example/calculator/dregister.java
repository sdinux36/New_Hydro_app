package com.example.calculator;
//IM/2020/009

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class dregister extends AppCompatActivity {
    TextView editTextEmail, editTextPassword, textView;
    Button buttonReg;
    ProgressBar progressbar;
    FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), dMainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dactivity_register);
         //initializing the objects
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.pswd);
        buttonReg=findViewById(R.id.registerbtn);
        progressbar=findViewById(R.id.progressbar);
        mAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.loginnow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),dLogin.class);
                startActivity(intent);
                //finish();
            }

        });


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(VISIBLE );
                String email,pswd;
                email=String.valueOf(editTextEmail.getText());
                pswd=String.valueOf(editTextPassword.getText());


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(dregister.this,"Enter Email ",Toast.LENGTH_SHORT).show();
                    return;
                }
                //email validation
                else if (!isValidEmail(email)) {
                    Toast.makeText(dregister.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(pswd)){
                    Toast.makeText(dregister.this,"Enter pswd ",Toast.LENGTH_SHORT).show();
                    return;
                }
                //password validation
                else if (!isValidPassword(pswd)) {
                    Toast.makeText(dregister.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, pswd)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(dregister.this, "Account created.",Toast.LENGTH_SHORT).show();
                                    //testing login
                                    Intent intent=new Intent(getApplicationContext(),dMainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(dregister.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }

            });
        }

    //password and email checking methods
    private boolean isValidPassword(String pswd) {
        return false;
    }

    private boolean isValidEmail(String email) {

        return false;
    }
}

