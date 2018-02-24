package com.example.phosphor.idecided;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class leadersListAdapter extends ArrayAdapter<leadersListModel> {

    private Activity context;
    private List<leadersListModel> leadersListModelList;

    @SuppressWarnings("WeakerAccess")
    protected leadersListAdapter(Activity context, List<leadersListModel> leadersListModelList) {
        super(context, R.layout.leaders_list_layout, leadersListModelList);
        this.context = context;
        this.leadersListModelList = leadersListModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.leaders_list_layout, parent, false);

            TextView textViewfirstname = convertView.findViewById(R.id.textViewfirstname);
            TextView textViewsecondname = convertView.findViewById(R.id.textViewSecondname);
            TextView textViewsurname = convertView.findViewById(R.id.textViewSurname);
            TextView textViewLeaderpost = convertView.findViewById(R.id.textViewLeaderPost);
            ImageView imageViewLeaderimage = convertView.findViewById(R.id.imageViewLeaderimage);


            leadersListModel leadersListModel_Obj = leadersListModelList.get(position);
            textViewfirstname.setText(leadersListModel_Obj.getFirstname());
            textViewsecondname.setText(leadersListModel_Obj.getSecondname());
            textViewsurname.setText(leadersListModel_Obj.getSurname());
            textViewLeaderpost.setText(leadersListModel_Obj.getPost());
            Glide.with(convertView).load(leadersListModel_Obj.getImage()).into(imageViewLeaderimage);

        }
        return convertView;
    }
}
