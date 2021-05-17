package com.example.cinemaera;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class SearchAdapter extends BaseAdapter implements Filterable {
    Context ctx;
    List<Film> OriginalSearch_film;
    List<Film> DisplaySearchFilm;
    LayoutInflater inflater;

    public SearchAdapter(Context ctx, List<Film> OriginalSearch_film) {
        this.ctx = ctx;
        this.OriginalSearch_film = OriginalSearch_film;
        this.DisplaySearchFilm = OriginalSearch_film;
    }

    @Override
    public int getCount() {
        return DisplaySearchFilm.size();
    }

    @Override
    public Object getItem(int position) {
        return DisplaySearchFilm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.custom_search,null);
        }
        LinearLayout SearchListView = convertView.findViewById(R.id.SearchListView);
        TextView Film_name = convertView.findViewById(R.id.film_text);
        ImageView film_image = convertView.findViewById(R.id.film_image);
        Film_name.setText(DisplaySearchFilm.get(position).getFilm_name());
        Picasso.get().load(DisplaySearchFilm.get(position).getFilm_image()).into(film_image);
        SearchListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(ctx ,Film_gallery.class);
                gallery.putExtra("movies_id",DisplaySearchFilm.get(position).getId());
                gallery.putExtra("Film images",DisplaySearchFilm.get(position).getFilm_image());
                gallery.putExtra("Film names",DisplaySearchFilm.get(position).getFilm_name());
                gallery.putExtra("Price",DisplaySearchFilm.get(position).getPrice());
                gallery.putExtra("Trailer video",DisplaySearchFilm.get(position).getTrailer_videos());
                gallery.putExtra("Full movies",DisplaySearchFilm.get(position).getFull_movies());
                gallery.putExtra("Cast",DisplaySearchFilm.get(position).getCast());
                gallery.putExtra("Director",DisplaySearchFilm.get(position).getDirector());
                gallery.putExtra("Release date",DisplaySearchFilm.get(position).getDate());
                gallery.putExtra("Run time",DisplaySearchFilm.get(position).getTime());
                gallery.putExtra("Language",DisplaySearchFilm.get(position).getLanguage());
                gallery.putExtra("Overview",DisplaySearchFilm.get(position).getOverview());
                gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(gallery);
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
       Filter filter = new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence constraint) {
               FilterResults filterResults = new FilterResults();
               if (constraint == null ) {
                   filterResults.count = OriginalSearch_film.size();
                   filterResults.values = OriginalSearch_film;
               } else {
                   List<Film> filterFilmList = new ArrayList<Film>();
                   String search = constraint.toString().toUpperCase();
                   for (Film SFilm : OriginalSearch_film) {
                       if ((SFilm.getFilm_name().toUpperCase()).contains(search)) {
                           filterFilmList.add(SFilm);
                       }
                       filterResults.count = filterFilmList.size();
                       filterResults.values = filterFilmList;
                   }
               }
               return filterResults;
           }
           @Override
           protected void publishResults(CharSequence constraint, FilterResults results) {
               DisplaySearchFilm = (List<Film>) results.values;
               notifyDataSetChanged();
           }

       };
       return filter;
    }
}
