package com.example.phosphor.idecided;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.phosphor.idecided.Adapters.CommentsRecyclerViewAdapter;
import com.example.phosphor.idecided.Model.CommentsModel;
import com.example.phosphor.idecided.Model.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.phosphor.idecided.Model.Constants.selectedChild;
import static com.example.phosphor.idecided.Model.Constants.selectedCounty;

public class CommentsActivity extends AppCompatActivity {

    private TextView textViewFname, textViewSname, textViewSurname;
    private ImageView imageViewLeader;
    private TextView textViewPost;
    private List<CommentsModel> commentsList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        commentsList = new ArrayList<>();

        textViewFname = findViewById(R.id.user_profile_firstname);
        textViewSname = findViewById(R.id.user_profile_secondname);
        textViewSurname = findViewById(R.id.user_profile_surname);
        textViewPost = findViewById(R.id.user_profile_post);
        imageViewLeader = findViewById(R.id.user_profile_photo);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toast.makeText(this, selectedCounty+selectedChild, Toast.LENGTH_SHORT).show();

        loadLeaderProfile();
        loadComments();

//        toolbar.setOnMenuItemClickListener(
//                new Toolbar.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.toolbar_comment:
//                                startActivity(new Intent(getApplicationContext(), addComment.class));
//                                return true;
//                            default:
//
//                        }
//                        return false;
//                    }
//                }
//        );
    }

    private void loadLeaderProfile(){
        Glide.with(getApplicationContext()).load(Constants.getImage()).into(imageViewLeader);
        textViewFname.setText(Constants.getFirstname());
        textViewSname.setText(Constants.getSecondname());
        textViewSurname.setText(Constants.getSurname());
        textViewPost.setText(Constants.getSurname() );
    }

    private void loadComments(){

        if (selectedChild.matches("Member of Parliament")) {
            DatabaseReference mDatabaseLoadComments = FirebaseDatabase.getInstance().getReference("CommentsActivity").
                                                        child(selectedCounty + selectedChild).child("");
        } else {
            DatabaseReference mDatabaseLoadComments = FirebaseDatabase.getInstance().getReference("CommentsActivity").child(selectedCounty + selectedChild.toLowerCase());
            mDatabaseLoadComments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot == null) {
                        Toast.makeText(getApplicationContext(),
                                "No comments for this user yet. Be the first to give your opinion!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            CommentsModel commentsmodel = postSnapshot.getValue(CommentsModel.class);
                            String timestamp = commentsmodel.getTimestamp();
                            Long long_timestamp = Long.valueOf(timestamp) / 1000;
                            commentsmodel.setConverted_timestamp(convertDate(long_timestamp));
                            commentsList.add(commentsmodel);
                        }
                        CommentsRecyclerViewAdapter adapter = new CommentsRecyclerViewAdapter(getApplicationContext(), commentsList);
                        recyclerView.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error loading comments: " + databaseError, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private String convertDate(Long time_ago){

        long cur_time = System.currentTimeMillis()/1000;
        long time_elapsed = cur_time - time_ago;
        int minutes = Math.round(time_elapsed / 60);
        int hours = Math.round(time_elapsed / 3600);
        int days = Math.round(time_elapsed / 86400);
        int weeks = Math.round(time_elapsed / 604800);
        int months = Math.round(time_elapsed / 2600640);
        int years = Math.round(time_elapsed / 31207680);

        if (years >= 1) {
            if (years == 1) {
                return years + " year ago";
            }else {
                return years + " years ago";
            }
        } else if (months >= 1) {
            if (months == 1) {
                return months + " month ago";
            }else {
                return months + " month ago";
            }
        } else if (weeks >= 1){
            if (weeks == 1) {
                return weeks + " week ago";
            }else {
                return weeks + " weeks ago";
            }
        } else if (days >= 1){
            if (days == 1) {
                return days + " day ago";
            }else {
                return days + " days ago";
            }
        }else if(hours >= 1){
            if (hours == 1) {
                return hours + " hour ago";
            }else {
                return hours + " hour ago";
            }
        }else if (minutes >= 1){
            if (minutes <= 5) {
                return "a few moments ago";
            }else {
                return minutes + " minutes ago";
            }
        }else {
            return "Just now";
        }

    }

}
