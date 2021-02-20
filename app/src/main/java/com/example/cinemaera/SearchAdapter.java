package com.example.cinemaera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class SearchAdapter extends BaseAdapter implements Filterable {
    Context ctx;
    List<Film> search_film;
    List<Film> searchFilm;
    LayoutInflater inflater;
    ValueFilter valueFilter;

    public SearchAdapter(Context ctx, List<Film> search_film) {
        this.ctx = ctx;
        this.search_film = search_film;
        searchFilm = search_film;
    }

    @Override
    public int getCount() {
        return search_film.size();
    }

    @Override
    public Object getItem(int position) {
        return search_film.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.custom_search,null);
        }
        TextView Film_name = convertView.findViewById(R.id.film_text);
        Film_name.setText(search_film.get(position).getFilm_name());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter ==  null){
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }
    class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0){
                List<Film> filterFilmList = new ArrayList<Film>();
                for (Film film: search_film){
                    if ((film.getFilm_name().toUpperCase()).contains(constraint.toString().toUpperCase()));
//                    Film film = new Film(searchFilm.get(i).getFilm_image(),searchFilm.get(i).getFilm_name(),searchFilm.get(i).getTrailer_videos(),searchFilm.get(i).getCast(),searchFilm.get(i).getDirector(),searchFilm.get(i).getDate(),searchFilm.get(i).getTime(),searchFilm.get(i).getLanguage(),searchFilm.get(i).getOverview());
                    filterFilmList.add(film);
                }
                filterResults.count = filterFilmList.size();
                filterResults.values = filterFilmList;
            }
            else {
                filterResults.count = searchFilm.size();
                filterResults.values = searchFilm;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            search_film = (ArrayList<Film>) results.values;
            notifyDataSetChanged();

        }
    }
}
