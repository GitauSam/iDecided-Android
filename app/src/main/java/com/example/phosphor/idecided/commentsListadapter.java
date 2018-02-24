package com.example.phosphor.idecided;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class commentsListadapter extends ArrayAdapter<commentsModel>{

    private Activity context;
    List<commentsModel> commentsModelList;


    public commentsListadapter(@NonNull Activity context, @NonNull List<commentsModel> commentsModelList) {
        super(context, R.layout.comments_list_layout, commentsModelList);
        this.context = context;
        this.commentsModelList = commentsModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.comments_list_layout, null);

        TextView textViewUsernameCmt = listViewItem.findViewById(R.id.textViewUsernamecmt);
        TextView textViewComment = listViewItem.findViewById(R.id.textViewComment);
        TextView textViewTimestamp = listViewItem.findViewById(R.id.textViewTSCmt);

        commentsModel commentsmodel = commentsModelList.get(position);

        String username = commentsmodel.getUsername();
        String comment = commentsmodel.getComment();
        String timestamp = commentsmodel.getTimestamp();

        textViewUsernameCmt.setText(username);
        textViewComment.setText(comment);
        textViewTimestamp.setText(timestamp);

        return listViewItem;

    }
}
