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
    List<Film.Search> OriginalSearchFilm;
    List<Film.Search> DisplaySearchFilm;
    LayoutInflater inflater;
    ValueFilter valueFilter;

    public SearchAdapter(Context ctx,List<Film.Search> OriginalSearchFilm) {
        this.ctx = ctx;
        this.OriginalSearchFilm = OriginalSearchFilm;
        this.DisplaySearchFilm = OriginalSearchFilm;
//        searchFilm = search_film;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.custom_search,null);
        }
        TextView Film_name = convertView.findViewById(R.id.film_text);
        Film_name.setText(DisplaySearchFilm.get(position).getFilm_name());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        }
    }
    class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                filterResults.count = OriginalSearchFilm.size();
                filterResults.values = OriginalSearchFilm;
                }
                filterResults.count = filterFilmList.size();
                filterResults.values = filterFilmList;
            }
            else {

            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            search_film = (ArrayList<Film.Search>) results.values;
            notifyDataSetChanged();

        }
    }

}
