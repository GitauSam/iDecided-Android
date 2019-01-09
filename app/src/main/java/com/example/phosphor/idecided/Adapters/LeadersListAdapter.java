package com.example.phosphor.idecided.Adapters;

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
import com.example.phosphor.idecided.Model.LeadersListModel;
import com.example.phosphor.idecided.R;

import java.util.List;

public class LeadersListAdapter extends ArrayAdapter<LeadersListModel> {

    private Activity context;
    private List<LeadersListModel> leadersListModelList;

    @SuppressWarnings("WeakerAccess")
    protected LeadersListAdapter(Activity context, List<LeadersListModel> leadersListModelList) {
        super(context, R.layout.layout_leaders_list, leadersListModelList);
        this.context = context;
        this.leadersListModelList = leadersListModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.layout_leaders_list, parent, false);

            TextView textViewfirstname = convertView.findViewById(R.id.textViewfirstname);
            TextView textViewsecondname = convertView.findViewById(R.id.textViewSecondname);
            TextView textViewsurname = convertView.findViewById(R.id.textViewSurname);
            TextView textViewLeaderpost = convertView.findViewById(R.id.textViewLeaderPost);
            ImageView imageViewLeaderimage = convertView.findViewById(R.id.imageViewLeaderimage);


            LeadersListModel leadersListModel_Obj = leadersListModelList.get(position);
            textViewfirstname.setText(leadersListModel_Obj.getFirstname());
            textViewsecondname.setText(leadersListModel_Obj.getSecondname());
            textViewsurname.setText(leadersListModel_Obj.getSurname());
            textViewLeaderpost.setText(leadersListModel_Obj.getPost());
            Glide.with(convertView).load(leadersListModel_Obj.getImage()).into(imageViewLeaderimage);

        }
        return convertView;
    }
}
