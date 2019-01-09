package com.example.phosphor.idecided.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.phosphor.idecided.Model.CommentsModel;
import com.example.phosphor.idecided.Adapters.CommentsRecyclerViewAdapter;
import com.example.phosphor.idecided.R;
import com.example.phosphor.idecided.SharedPrefs.SharedPrefManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.phosphor.idecided.Model.Constants.selectedChild;
import static com.example.phosphor.idecided.Model.Constants.selectedCounty;

public class LeaderCommentsFragment extends Fragment {

    private RecyclerView recyclerView;

    private DatabaseReference cDatabase;

    private ProgressBar progressBar;

    ArrayList<CommentsModel> commentsList;

    CommentsRecyclerViewAdapter adapter;

    public LeaderCommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leader_comments, container, false);
        progressBar = view.findViewById(R.id.progress_bar_fragment_comments);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button_add_comment);
        recyclerView = view.findViewById(R.id.recycler_view_comments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar.setVisibility(View.VISIBLE);

        loadComments();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
        return view;
    }

    private void loadComments(){

        commentsList = new ArrayList<>();

        if (selectedChild.matches("Member of Parliament")) {
            DatabaseReference mDatabaseLoadComments = FirebaseDatabase.getInstance().getReference("Comments").
                    child(selectedCounty+selectedChild).child("");
        } else {
            DatabaseReference mDatabaseLoadComments = FirebaseDatabase.getInstance().getReference("Comments").child(selectedCounty+selectedChild);
            mDatabaseLoadComments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.exists()) {
                        Toast.makeText(getContext(),
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
                        adapter = new CommentsRecyclerViewAdapter(getContext(), commentsList);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Error loading comments: " + databaseError, Toast.LENGTH_LONG).show();
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
    private void addComment() {
        cDatabase = FirebaseDatabase.getInstance().getReference("Comments").child(selectedCounty+selectedChild);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View commentLayout = inflater.inflate(R.layout.layout_add_comment, null);
        builder.setTitle("Enter Comment")
                .setView(commentLayout)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> newComment= new HashMap<>();
                        String key = cDatabase.push().getKey();
                        EditText editTextComment = commentLayout.findViewById(R.id.edit_text_enter_comment);
                        String comment = editTextComment.getText().toString();
                        newComment.put("comment", comment);
                        newComment.put("timestamp", String.valueOf(System.currentTimeMillis()));
                        newComment.put("username", SharedPrefManager.getInstance(getContext()).getUser().getUsername());
                        cDatabase.child(key).setValue(newComment);
                        commentsList.clear();
                        adapter.notifyDataSetChanged();
                        loadComments();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

}
