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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class SearchAdapter extends BaseAdapter implements Filterable {
    Context ctx;
    List<Film.Search> OriginalSearch_film;
    List<Film.Search> DisplaySearchFilm;
    LayoutInflater inflater;

    public SearchAdapter(Context ctx, List<Film.Search> OriginalSearch_film) {
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
        TextView Film_name = convertView.findViewById(R.id.film_text);
        Film_name.setText(DisplaySearchFilm.get(position).getFilm_name());
        Film_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, DisplaySearchFilm.get(position).getFilm_name(), Toast.LENGTH_LONG).show();
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
               if (constraint == null || constraint.length() == 0) {
                   filterResults.count = OriginalSearch_film.size();
                   filterResults.values = OriginalSearch_film;
               } else {
                   List<Film.Search> filterFilmList = new ArrayList<Film.Search>();
                   String search = constraint.toString().toUpperCase();
                   for (Film.Search SFilm : OriginalSearch_film) {
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
               DisplaySearchFilm = (List<Film.Search>) results.values;
               notifyDataSetChanged();
           }
       };
       return filter;
    }
}
