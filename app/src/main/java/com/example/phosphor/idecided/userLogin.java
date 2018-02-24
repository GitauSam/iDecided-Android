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

public class userLogin extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail, editTextPassword;
    TextView textViewSignup;
    LinearLayout linearLayoutSignin;

    FirebaseAuth mAuth;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, countyList.class));
            finish();
        }

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextUserpass);
        linearLayoutSignin = findViewById(R.id.linearLayoutLogin);
        textViewSignup = findViewById(R.id.textViewSignup);

        progressDialog = new ProgressDialog(this);

        linearLayoutSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    public void signinUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Signing in successful", Toast.LENGTH_SHORT).show();

                            Intent myIntent = new Intent(getApplicationContext(), countyList.class);
                            startActivity(myIntent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Signing in Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if (view == linearLayoutSignin){

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.matches("")||password.matches("")){
                Toast.makeText(getApplicationContext(), "Please fill in the relevant fields", Toast.LENGTH_SHORT).show();
            }else {
                signinUser();
            }
        }

        if(view == textViewSignup){
            startActivity(new Intent(getApplicationContext(), userRegistration.class));
        }
    }
}
