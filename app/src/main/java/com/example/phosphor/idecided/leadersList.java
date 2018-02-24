package com.example.phosphor.idecided;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class leadersList extends AppCompatActivity {

    private static final String TAG = "leadersList";

    DatabaseReference mDatabase;
    String Storage_Path;

    String selectedCounty;

    ListView leaderListView;
    List<leadersListModel> leadersArrayList;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders_list);

        selectedCounty = getIntent().getStringExtra("countyName");
        mDatabase = FirebaseDatabase.getInstance().getReference(selectedCounty);
        Storage_Path = selectedCounty + "/";

        leaderListView = findViewById(R.id.listViewLeaders);

        progressBar = findViewById(R.id.progressBar);

        leadersArrayList = new ArrayList<>();

        loadLeadersList();
    }

    private void loadLeadersList(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    leadersListModel leadersListModel_Obj = postSnapshot.getValue(leadersListModel.class);
                    leadersArrayList.add(leadersListModel_Obj);
                }

                leadersListAdapter listAdapter = new leadersListAdapter(leadersList.this, leadersArrayList);
                leaderListView.setAdapter(listAdapter);

                progressBar.setVisibility(View.INVISIBLE);

                leaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int Position, long id){

                        String selectedChildId = String.valueOf(Position);
                        String selectedChild = "leader_id_" + selectedChildId;
                        Intent myIntent = new Intent(leadersList.this, leaderProfile.class);
                        myIntent.putExtra("selectedCounty" , selectedCounty);
                        myIntent.putExtra("selectedChild" , selectedChild);
                        startActivity(myIntent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "Failed to read value.", databaseError.toException());

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
                finish();
                startActivity(new Intent(this, userLogin.class));
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG).show();
        }
        return true;
    }


}
