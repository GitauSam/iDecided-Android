package com.example.phosphor.idecided.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phosphor.idecided.Model.LeadersListModel;
import com.example.phosphor.idecided.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Phosphor on 3/24/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listPost;
    private HashMap<String, List<LeadersListModel>> leaderDetails;

    public ExpandableListAdapter(Context context, List<String> listPost, HashMap<String, List<LeadersListModel>> leaderDetails) {
        this.context = context;
        this.listPost = listPost;
        this.leaderDetails = leaderDetails;
    }

    @Override
    public int getGroupCount() {
        return listPost.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return leaderDetails.get(listPost.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listPost.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return leaderDetails.get(listPost.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.list_group, parent, false);
        }
        TextView listHeader = convertView.findViewById(R.id.textViewHeader);

        listHeader.setText((String) getGroup(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.layout_leaders_list, parent, false);
        }
        ImageView imageViewLeader = convertView.findViewById(R.id.imageViewLeaderimage);
        TextView firstname = convertView.findViewById(R.id.textViewfirstname);
        TextView secondname = convertView.findViewById(R.id.textViewSecondname);
        TextView surname = convertView.findViewById(R.id.textViewSurname);
        TextView post = convertView.findViewById(R.id.textViewLeaderPost);

        LeadersListModel leader;
        leader = (LeadersListModel) getChild(groupPosition, childPosition);
        firstname.setText(leader.getFirstname());
        secondname.setText(leader.getSecondname());
        surname.setText(leader.getSurname());
        post.setText(leader.getPost());
        Glide.with(parent.getContext()).load(leader.getImage()).into(imageViewLeader);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
