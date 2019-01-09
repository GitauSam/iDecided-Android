package com.example.phosphor.idecided;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phosphor.idecided.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextUsername;
    private Button buttonRegister;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabaseUsers;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        editTextEmail = findViewById(R.id.editTextUseremail);
        editTextPassword = findViewById(R.id.editTextUserpass);
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonRegister = findViewById(R.id.btnRegister);
        textViewSignin = findViewById(R.id.textSignIn);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Usernames");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    public void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        username = editTextUsername.getText().toString().trim();

        progressDialog.setMessage("Registering...");
        progressDialog.show();

       mAuth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            createNewUser(task.getResult().getUser());

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent myIntent = new Intent(UserRegistrationActivity.this, CountyListActivity.class);
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

    private void createNewUser(FirebaseUser userFromRegistration) {
        String email = userFromRegistration.getEmail();
        String userId = userFromRegistration.getUid();

        User user = new User(username, email);

        mDatabaseUsers.child(userId).setValue(user);
    }

    @Override
    public void onClick(View view) {

        if (view == buttonRegister){
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
            startActivity(new Intent(UserRegistrationActivity.this, UserLoginActivity.class));
//            new Intent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }

    }
}
