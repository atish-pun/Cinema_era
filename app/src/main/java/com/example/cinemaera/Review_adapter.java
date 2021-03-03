package com.example.cinemaera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class Review_adapter extends RecyclerView.Adapter<Review_adapter.ViewHolder> {
    Context context;
    List<Film.ReviewInfo> reviewInfo = new ArrayList<>();

    public Review_adapter(Context context, List<Film.ReviewInfo> reviewInfo){
        this.context = context;
        this.reviewInfo = reviewInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_review,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.reviewOutput.setText(reviewInfo.get(position).getReviews());
        holder.userName.setText(reviewInfo.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return reviewInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView reviewOutput, userName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewOutput = itemView.findViewById(R.id.reviewOutput);
            userName = itemView.findViewById(R.id.Username);
        }
    }
}
