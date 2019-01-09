package com.example.phosphor.idecided;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.phosphor.idecided.SharedPrefs.SharedPrefManager;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    protected void saveUsername() {

    }
    protected void signOut() {

        SharedPrefManager.getInstance(getApplicationContext()).logOut();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(this, UserLoginActivity.class));
        finish();

    }
}
