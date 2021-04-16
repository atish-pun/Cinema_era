package com.example.cinemaera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class MovieSliderAdapter extends SliderViewAdapter<MovieSliderAdapter.SliderViewHolder> {
    Context ctx;
    List<Film> movieSlider = new ArrayList<>();

    public MovieSliderAdapter(Context ctx, List<Film> movieSlider) {
        this.ctx = ctx;
        this.movieSlider = movieSlider;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movies_slider_layout,null);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Picasso.get().load(movieSlider.get(position).getFilm_image()).into(viewHolder.movie_image);
        viewHolder.movie_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(ctx ,Film_gallery.class);
                gallery.putExtra("movies_id",movieSlider.get(position).getId());
                gallery.putExtra("Film images",movieSlider.get(position).getFilm_image());
                gallery.putExtra("Film names",movieSlider.get(position).getFilm_name());
                gallery.putExtra("Price",movieSlider.get(position).getPrice());
                gallery.putExtra("Trailer video",movieSlider.get(position).getTrailer_videos());
                gallery.putExtra("Cast",movieSlider.get(position).getCast());
                gallery.putExtra("Director",movieSlider.get(position).getDirector());
                gallery.putExtra("Release date",movieSlider.get(position).getDate());
                gallery.putExtra("Run time",movieSlider.get(position).getTime());
                gallery.putExtra("Language",movieSlider.get(position).getLanguage());
                gallery.putExtra("Overview",movieSlider.get(position).getOverview());
                gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(gallery);
            }
        });

    }

    @Override
    public int getCount() {
        return movieSlider.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder{
        private ImageView movie_image;
        public SliderViewHolder(View itemView) {
            super(itemView);
            movie_image = itemView.findViewById(R.id.MoviesCustomSlider);
        }
    }
}
