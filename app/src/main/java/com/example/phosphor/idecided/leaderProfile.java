package com.example.phosphor.idecided;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class leaderProfile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    String selectedCounty, selectedChild;

    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_profile);

        selectedCounty = getIntent().getStringExtra("selectedCounty");
        selectedChild = getIntent().getStringExtra("selectedChild");

        mDatabase = FirebaseDatabase.getInstance().getReference(selectedCounty).child(selectedChild);
        mAuth = FirebaseAuth.getInstance();

        myIntent = new Intent(this, Comments.class);

        loadDetails();
    }

    private void loadDetails(){

        final ImageView userProfile =  findViewById(R.id.user_profile_photo);

        final TextView textViewFname =  findViewById(R.id.user_profile_firstname);

        final TextView textViewSname =  findViewById(R.id.user_profile_secondname);

        final TextView textViewSurname =  findViewById(R.id.user_profile_surname);

        final TextView textViewPost =  findViewById(R.id.user_profile_post);

        final TextView textViewParty = findViewById(R.id.user_profile_party);

        final TextView textViewArea = findViewById(R.id.user_profile_area);

        final TextView textViewEduc = findViewById(R.id.user_profile_education);

        final TextView textViewAwards = findViewById(R.id.user_profile_awards);

        final TextView textViewOffice = findViewById(R.id.user_profile_inoffice);

        //TextView textViewManifesto = findViewById(R.id.user_profile_manifesto);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Glide.with(leaderProfile.this).load(dataSnapshot.child("image").getValue()).into(userProfile);
                textViewFname.setText((String) dataSnapshot.child("firstname").getValue());
                textViewSname.setText((String) dataSnapshot.child("secondname").getValue());
                textViewSurname.setText((String) dataSnapshot.child("surname").getValue());
                textViewPost.setText((String) dataSnapshot.child("post").getValue());
                textViewParty.setText((String) dataSnapshot.child("party").getValue());
                textViewArea.setText((String) dataSnapshot.child("areaofjurisdiction").getValue());
                textViewEduc.setText((String) dataSnapshot.child("education").getValue());
                textViewAwards.setText((String) dataSnapshot.child("awards").getValue());
                textViewOffice.setText((String) dataSnapshot.child("office").getValue());
                //textViewManifesto.setText((String) dataSnapshot.child("manifesto").getValue());

                myIntent.putExtra("firstname" , (String) dataSnapshot.child("firstname").getValue());
                myIntent.putExtra("secondname" , (String) dataSnapshot.child("secondname").getValue());
                myIntent.putExtra("surname" , (String) dataSnapshot.child("suurname").getValue());
                myIntent.putExtra("image" , (String) dataSnapshot.child("image").getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, userLogin.class));
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void showPopup(View view){

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(leaderProfile.this);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.popupComment:
                myIntent.putExtra("selectedCounty", selectedCounty);
                myIntent.putExtra("selectedChild", selectedChild);
                startActivity(myIntent);
                return true;
        }
        return false;
    }
}
