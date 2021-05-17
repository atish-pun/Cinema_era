package com.example.cinemaera;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class Movies_transaction_adpater extends RecyclerView.Adapter<Movies_transaction_adpater.ViewHolder> {
    Context ctx;
    List<Film.TransactedMovieInfo> transactedMovies = new ArrayList<>();
    public Movies_transaction_adpater(Context ctx, List<Film.TransactedMovieInfo> transactedMovies) {
        this.ctx = ctx;
        this.transactedMovies = transactedMovies;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_transaction_movies,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.film_name.setText(transactedMovies.get(position).getA_name());
        Picasso.get().load(transactedMovies.get(position).getA_poster()).into(holder.film_image);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(ctx ,Film_gallery.class);
                gallery.putExtra("Film images",transactedMovies.get(position).getA_poster());
                gallery.putExtra("Film names",transactedMovies.get(position).getA_name());
                gallery.putExtra("Price",transactedMovies.get(position).getPrice());
                gallery.putExtra("Trailer video",transactedMovies.get(position).getTrailer_videos());
                gallery.putExtra("Full movies",transactedMovies.get(position).getFull_movies());
                gallery.putExtra("Cast",transactedMovies.get(position).getCast());
                gallery.putExtra("Director",transactedMovies.get(position).getDirector());
                gallery.putExtra("Release date",transactedMovies.get(position).getRelease_date());
                gallery.putExtra("Run time",transactedMovies.get(position).getRun_time());
                gallery.putExtra("Language",transactedMovies.get(position).getLanguage());
                gallery.putExtra("Overview",transactedMovies.get(position).getOverview());
                gallery.putExtra("movies_id",transactedMovies.get(position).getMovies_id());
                gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(gallery);

            }
        });
    }

    @Override
    public int getItemCount() {
        return transactedMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView film_name;
        ImageView film_image;
        LinearLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            film_name = itemView.findViewById(R.id.film_text);
            film_image = itemView.findViewById(R.id.film_image);
            parent_layout = itemView.findViewById(R.id.view_custom);
        }
    }
}
