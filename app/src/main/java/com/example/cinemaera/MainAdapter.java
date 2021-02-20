package com.example.cinemaera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Context ctx;
    List<FilmCategoryName> filmCategoryNames = new ArrayList<>();
    public MainAdapter(Context ctx, List<FilmCategoryName> filmCategoryNames) {
        this.ctx = ctx;
        this.filmCategoryNames = filmCategoryNames;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View views = inflater.inflate(R.layout.main_film_category_item,parent,false);
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, final int position) {
        holder.categoryName.setText(filmCategoryNames.get(position).getCategoryname());
        setFilmRecycler(holder.film_recycler,filmCategoryNames.get(position).getFilms());

        if (filmCategoryNames.get(position).getId() == 1) {

            holder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(ctx, ActionViewAllFilm.class);
                    ctx.startActivity(gallery);
                }

            });
        }
        else if (filmCategoryNames.get(position).getId() == 2){

            holder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(ctx , LoveViewAllFilm.class);
                    ctx.startActivity(gallery);
                }
            });
        }
        else if (filmCategoryNames.get(position).getId() == 3) {
            holder.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gallery = new Intent(ctx, HorrorViewAllFilm.class);
                    ctx.startActivity(gallery);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return filmCategoryNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName, viewAll;
        RecyclerView film_recycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_text);
            viewAll = itemView.findViewById(R.id.view_all);
            film_recycler = itemView.findViewById(R.id.film_recycler);
        }
    }
    private void setFilmRecycler(RecyclerView recyclerView,List<Film> films){
        Adapter_film adapter_film = new Adapter_film(ctx,films);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(adapter_film);
    }
}


