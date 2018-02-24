package com.example.phosphor.idecided;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class userRegistration extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword, editTextUsername;
    LinearLayout linearLayoutRegister;
    TextView textViewSignin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        editTextEmail = findViewById(R.id.editTextUseremail);
        editTextPassword = findViewById(R.id.editTextUserpass);
        editTextUsername = findViewById(R.id.editTextUsername);
        linearLayoutRegister = findViewById(R.id.linearLayoutSignUpWidget);
        textViewSignin = findViewById(R.id.textSignIn);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        linearLayoutRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    public void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String username = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String plainEmail = email.replaceAll("[.@]","");

                            mDatabase = FirebaseDatabase.getInstance().getReference("Usernames").child(plainEmail);

                            Map<String, String> usernameHashmap = new HashMap<>();

                            usernameHashmap.put(plainEmail, username);

                            mDatabase.setValue(usernameHashmap);

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent myIntent = new Intent(getApplicationContext(), countyList.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration is Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if (view == linearLayoutRegister){

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String username = editTextUsername.getText().toString().trim();

            if (email.matches("")||password.matches("")||username.matches("")){
                Toast.makeText(getApplicationContext(),"Please fill in the relevant fields",Toast.LENGTH_SHORT).show();
            }else {
                registerUser();
            }
        }

        if (view == textViewSignin){

            startActivity(new Intent(getApplicationContext(), userLogin.class));
        }

    }
}
