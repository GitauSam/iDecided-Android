package com.example.phosphor.idecided;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Comments extends AppCompatActivity implements View.OnClickListener {

    //listview object
    ListView listView;

    //first name textview
    TextView textViewFname, textViewSname, textViewSurname;

    //strs to enter
    String selectedCounty, selectedChild, comment_id, formattedEmail, outputDate, username;

    //leader imageview
    ImageView imageViewLeader;

    //post textview
    TextView textViewPost;

    //Edittext object
    EditText editTextComment;

    //Button object
    Button btnEnterComment;

    //the hero list where we will store all the Comments_constants objects after parsing json
    List<commentsModel> commentsList;

    DatabaseReference mDatabase,mDatabaseUsername;

    FirebaseUser user;

    Long tsLong, tsLongConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_comments);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseUsername = FirebaseDatabase.getInstance().getReference("Usernames");

        selectedCounty = getIntent().getStringExtra("selectedCounty");
        selectedChild = getIntent().getStringExtra("selectedChild");
        comment_id = selectedCounty + selectedChild;

        //initializing listview and comments arraylist
        listView = findViewById(R.id.listViewComments);
        commentsList = new ArrayList<>();

        //initialising edittext
        editTextComment = findViewById(R.id.editTextenterComment);

        //initialising btn obj
        btnEnterComment = findViewById(R.id.btnEnterComment);

        textViewFname = findViewById(R.id.user_profile_firstname);
        textViewSname = findViewById(R.id.user_profile_secondname);
        textViewSurname = findViewById(R.id.user_profile_surname);
        textViewPost = findViewById(R.id.user_profile_post);
        imageViewLeader = findViewById(R.id.user_profile_photo);
        btnEnterComment.setOnClickListener(this);

        formattedEmail = user.getEmail().replaceAll("[.@]","");

        mDatabaseUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    username = (String) postSnapShot.child(formattedEmail).getValue();
                    break;
                }
                Toast.makeText(getApplicationContext(), "Username is: " + username, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error while adding comment: " + databaseError,Toast.LENGTH_LONG).show();
            }
        });

        loadLeaderProfile();
        loadComments();
    }

    private void loadLeaderProfile(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child(selectedCounty).child(selectedChild).child("image").getValue()).into(imageViewLeader);
                textViewFname.setText((String) dataSnapshot.child(selectedCounty).child(selectedChild).child("firstname").getValue());
                textViewSname.setText((String) dataSnapshot.child(selectedCounty).child(selectedChild).child("secondname").getValue());
                textViewSurname.setText((String) dataSnapshot.child(selectedCounty).child(selectedChild).child("surname").getValue());
                textViewPost.setText((String) dataSnapshot.child(selectedCounty).child(selectedChild).child("post").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error loading leaders's profile: " + databaseError,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComments(){

        DatabaseReference mDatabaseLoadComments;
        mDatabaseLoadComments = FirebaseDatabase.getInstance().getReference("Comments").child(comment_id);

        mDatabaseLoadComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot == null) {
                    Toast.makeText(getApplicationContext(), "No comments for this user yet. Be the first to give your opinion!",
                            Toast.LENGTH_LONG).show();
                } else {


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        commentsModel commentsmodel = postSnapshot.getValue(commentsModel.class);
                        //Toast.makeText(getApplication(), commentsmodel.getTimestamp(),Toast.LENGTH_LONG).show();
                        convertDate(tsLongConvert.valueOf(commentsmodel.getTimestamp()));
                        commentsmodel.setTimestamp(outputDate);
                        commentsList.add(commentsmodel);
                    }

                    commentsListadapter commentslistadapter = new commentsListadapter(Comments.this, commentsList);
                    listView.setAdapter(commentslistadapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error loading comments: " + databaseError, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void addComment(){

        final String comment = editTextComment.getText().toString().trim();
        editTextComment.setText(null);
        DatabaseReference mDatabaseAddComment = FirebaseDatabase.getInstance().getReference("Comments");
        final HashMap<String, String> mapComment = new HashMap<>();
        tsLong = System.currentTimeMillis();

        if (user != null){
            //Toast.makeText(getApplicationContext(),formattedEmail,Toast.LENGTH_LONG).show();
            String key = mDatabaseAddComment.child(comment_id).push().getKey();
            mapComment.put("username", username);
            mapComment.put("comment", comment);
            mapComment.put("timestamp",String.valueOf(tsLong));
            mDatabaseAddComment.child(comment_id).child(key).setValue(mapComment);
            //loadComments();
        }

    }

    private void convertDate(Long timestampRet){

        //DateTimeZone timeZone = DateTimeZone.getDefault();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM yyyy");
        DateTime dateTime = new DateTime(timestampRet);
        outputDate = formatter.print(dateTime);

    }

    @Override
    public void onClick(View view) {
        if (view == btnEnterComment){

            commentsList.clear();
            addComment();
        }
    }
}
