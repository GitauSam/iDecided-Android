package com.example.phosphor.idecided;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.phosphor.idecided.Adapters.ExpandableListAdapter;
import com.example.phosphor.idecided.Model.LeadersListModel;
import com.example.phosphor.idecided.Model.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeadersExpListViewActivity extends BaseActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter adapter;
    List<String> listPost;
    HashMap<String, List<LeadersListModel>> leaderDetails;

    FirebaseAuth mAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders_exp_list_vew);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_bar_exp_listview);
        //progressBar = new ProgressBar(this);

        expandableListView = findViewById(R.id.leadersListExpList);
        expandableListView.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        String selectedChild = listPost.get(groupPosition);
                        Constants.selectedChild = selectedChild.toLowerCase();
                        LeadersListModel leader;
                        leader = leaderDetails.get(selectedChild).get(childPosition);
                        Constants.firstname = leader.getFirstname();
                        Constants.secondname = leader.getSecondname();
                        Constants.surname = leader.getSurname();
                        Constants.image = leader.getImage();
                        Constants.post = leader.getPost();
                        Constants.party = leader.getParty();
                        Constants.areaofjurisdiction = leader.getAreaofjurisdiction();
                        Constants.awards = leader.getAwards();
                        Constants.education = leader.getEducation();
                        Constants.manifesto = leader.getManifesto();
                        Constants.office = leader.getOffice();

                        startActivity(new Intent(LeadersExpListViewActivity.this, LeaderDetailsActivity.class));
                        return false;
                    }
                });

        listPost = new ArrayList<>();
        leaderDetails = new HashMap<>();

        progressBar.setVisibility(View.VISIBLE);
        addPostList();
        loadLeadersList();
    }

    private void addPostList() {
        listPost.add("Governor");
        listPost.add("Senator");
        listPost.add("Women Representative");
        listPost.add("Members of Parliament");
    }

    private void loadLeadersList() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.selectedCounty);
        final List<LeadersListModel> governor = new ArrayList<>();
        final List<LeadersListModel> senator = new ArrayList<>();
        final List<LeadersListModel> womenrep = new ArrayList<>();
        final List<LeadersListModel> mop = new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String postsnapshotKey = postSnapshot.getKey();
                            LeadersListModel leader = postSnapshot.getValue(LeadersListModel.class);
                            if (postsnapshotKey.matches("governor")) {
                                governor.add(leader);
                            } else if (postsnapshotKey.matches("senator")) {
                                senator.add(leader);
                            } else if (postsnapshotKey.matches("womensrep")) {
                                womenrep.add(leader);
                            } else {
                                mop.add(leader);
                            }

                        }
                        leaderDetails.put(listPost.get(0), governor);
                        leaderDetails.put(listPost.get(1), senator);
                        leaderDetails.put(listPost.get(2), womenrep);
                        leaderDetails.put(listPost.get(3), mop);

                        adapter = new ExpandableListAdapter(LeadersExpListViewActivity.this, listPost, leaderDetails);

                        progressBar.setVisibility(View.INVISIBLE);

                        expandableListView.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Database Error " + databaseError, Toast.LENGTH_LONG).show();
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
                signOut();
                startActivity(new Intent(this, UserLoginActivity.class));
                finish();
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG).show();
        }
        return true;
    }

}
