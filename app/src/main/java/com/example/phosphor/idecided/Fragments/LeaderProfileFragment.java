package com.example.phosphor.idecided.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phosphor.idecided.Model.Constants;
import com.example.phosphor.idecided.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaderProfileFragment extends Fragment {

    View view;

    TextView firstName, middleName, surame, post, party, area, education, awards, stintDuration,
            manifesto;

    ImageView imageViewProfile;

    public LeaderProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_leader_profile, container, false);

        imageViewProfile = view.findViewById(R.id.user_profile_photo);

        firstName = view.findViewById(R.id.user_profile_firstname);
        middleName = view.findViewById(R.id.user_profile_secondname);
        surame = view.findViewById(R.id.user_profile_surname);
        post = view.findViewById(R.id.user_profile_post);
        party = view.findViewById(R.id.user_profile_party);
        area = view.findViewById(R.id.user_profile_area);
        education = view.findViewById(R.id.user_profile_education);
        awards = view.findViewById(R.id.user_profile_awards);
        stintDuration = view.findViewById(R.id.user_profile_inoffice);

        loadDetails();

        return view;

    }

    private void loadDetails(){

        Glide.with(getActivity()).load(Constants.image).into(imageViewProfile);

        firstName.setText(Constants.firstname);
        middleName.setText(Constants.secondname);
        surame.setText(Constants.surname);
        post.setText(Constants.post);
        party.setText(Constants.party);
        area.setText(Constants.areaofjurisdiction);
        education.setText(Constants.education);
        awards.setText(Constants.awards);
        stintDuration.setText(Constants.office);
    }
}
