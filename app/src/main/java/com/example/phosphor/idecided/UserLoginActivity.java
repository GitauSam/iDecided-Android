package com.example.phosphor.idecided;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phosphor.idecided.Model.User;
import com.example.phosphor.idecided.SharedPrefs.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail, editTextPassword;
    private TextView textViewSignup;
    private Button buttonLogin;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    private String username;

    private User userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, CountyListActivity.class));
            finish();
        }

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextUserpass);
        buttonLogin = findViewById(R.id.btnLogin);
        textViewSignup = findViewById(R.id.textViewSignup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        buttonLogin.setOnClickListener(this);
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

                            getUsername();

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Signing in successful", Toast.LENGTH_SHORT).show();

                            Intent myIntent = new Intent(getApplicationContext(), CountyListActivity.class);
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

        if (view == buttonLogin){

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.matches("")||password.matches("")){
                Toast.makeText(getApplicationContext(), "Please fill in the relevant fields", Toast.LENGTH_SHORT).show();
            }else {
                signinUser();
            }
        }

        if(view == textViewSignup){
            startActivity(new Intent(getApplicationContext(), UserRegistrationActivity.class));
            finish();
        }
    }

    private void getUsername() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference uDatabase = FirebaseDatabase.getInstance().getReference("Usernames").child(user.getUid());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userModel = dataSnapshot.getValue(User.class);
                username = userModel.getUsername();
                Log.w("Username", "Username" + username);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(new User(username));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Username", "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(), "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        uDatabase.addValueEventListener(postListener);
    }
}
