package com.example.phosphor.idecided.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phosphor.idecided.Model.CommentsModel;
import com.example.phosphor.idecided.R;

import java.util.List;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.MyViewHolder> {

    private List<CommentsModel> commentsModels;

    public CommentsRecyclerViewAdapter(Context context, List<CommentsModel> commentsModels) {
        Context context1 = context;
        this.commentsModels = commentsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.comments_list_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView username, comment, timestamp;
        private MyViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewUsername);
            comment = itemView.findViewById(R.id.textViewComment);
            timestamp = itemView.findViewById(R.id.textViewTimestamp);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentsModel commentsModelObj = commentsModels.get(position);
        holder.username.setText(commentsModelObj.getUsername());
        holder.comment.setText(commentsModelObj.getComment());
        holder.timestamp.setText(commentsModelObj.getConverted_timestamp());
    }

    @Override
    public int getItemCount() {
        return commentsModels.size();
    }

}
