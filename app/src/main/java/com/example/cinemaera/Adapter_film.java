package com.example.cinemaera;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

class Adapter_film extends RecyclerView.Adapter<Adapter_film.ViewHolder> {
    Context ctx;
    List<Film> films = new ArrayList<>();
    public Adapter_film(Context ctx,List<Film>films){
        this.ctx = ctx;
        this.films=films;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View views = inflater.inflate(R.layout.custom_film,parent,false);
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.film_txt.setText(films.get(position).getFilm_name());
        Picasso.get().load(films.get(position).getFilm_image()).into(holder.film_img);
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(ctx ,Film_gallery.class);
                gallery.putExtra("movies_id",films.get(position).getId());
                gallery.putExtra("Film images",films.get(position).getFilm_image());
                gallery.putExtra("Film names",films.get(position).getFilm_name());
                gallery.putExtra("Price",films.get(position).getPrice());
                gallery.putExtra("Trailer video",films.get(position).getTrailer_videos());
                gallery.putExtra("Cast",films.get(position).getCast());
                gallery.putExtra("Director",films.get(position).getDirector());
                gallery.putExtra("Release date",films.get(position).getDate());
                gallery.putExtra("Run time",films.get(position).getTime());
                gallery.putExtra("Language",films.get(position).getLanguage());
                gallery.putExtra("Overview",films.get(position).getOverview());
                gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(gallery);

            }
        });

    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView film_img;
        TextView film_txt;
        ConstraintLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            film_img = itemView.findViewById(R.id.film_image);
            film_txt = itemView.findViewById(R.id.film_text);
            parent_layout = itemView.findViewById(R.id.custom_layout);
        }
    }
}
