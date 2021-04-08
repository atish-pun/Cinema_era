package com.example.cinemaera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class ViewAllFilmAdapter extends RecyclerView.Adapter<ViewAllFilmAdapter.ViewHolder> {
    Context ctx;
    List<Film> actionMoviesAll = new ArrayList<>();
    public ViewAllFilmAdapter(Context ctx, List<Film> actionMoviesAll) {
        this.ctx = ctx;
        this.actionMoviesAll = actionMoviesAll;

}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_view_all_films,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.film_name.setText(actionMoviesAll.get(position).getFilm_name());
        Picasso.get().load(actionMoviesAll.get(position).getFilm_image()).into(holder.film_image);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(ctx ,Film_gallery.class);
                gallery.putExtra("movies_id",actionMoviesAll.get(position).getId());
                gallery.putExtra("Film images",actionMoviesAll.get(position).getFilm_image());
                gallery.putExtra("Film names",actionMoviesAll.get(position).getFilm_name());
                gallery.putExtra("Price",actionMoviesAll.get(position).getPrice());
                gallery.putExtra("Trailer video",actionMoviesAll.get(position).getTrailer_videos());
                gallery.putExtra("Cast",actionMoviesAll.get(position).getCast());
                gallery.putExtra("Director",actionMoviesAll.get(position).getDirector());
                gallery.putExtra("Release date",actionMoviesAll.get(position).getDate());
                gallery.putExtra("Run time",actionMoviesAll.get(position).getTime());
                gallery.putExtra("Language",actionMoviesAll.get(position).getLanguage());
                gallery.putExtra("Overview",actionMoviesAll.get(position).getOverview());
                gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(gallery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return actionMoviesAll.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView film_name;
        ImageView film_image;
        ConstraintLayout parent_layout;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        film_name = itemView.findViewById(R.id.film_text);
        film_image = itemView.findViewById(R.id.film_image);
        parent_layout = itemView.findViewById(R.id.view_custom);
    }
}
}
