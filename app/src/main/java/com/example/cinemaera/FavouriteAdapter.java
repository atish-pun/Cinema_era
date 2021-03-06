package com.example.cinemaera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    Context ctx;
    List<Film.FavouriteInfo> favourite = new ArrayList<>();

    public FavouriteAdapter(Context ctx, List<Film.FavouriteInfo> favourite) {
        this.ctx = ctx;
        this.favourite = favourite;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favourite_custom_list,parent,false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.film_name.setText(favourite.get(position).getA_name());
        holder.RunTime.setText(favourite.get(position).getRun_time());
        Picasso.get().load(favourite.get(position).getA_poster()).into(holder.film_image);
        holder.film_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(ctx ,Film_gallery.class);
                gallery.putExtra("Film images",favourite.get(position).getA_poster());
                gallery.putExtra("Film names",favourite.get(position).getA_name());
                gallery.putExtra("Trailer video",favourite.get(position).getTrailer_videos());
                gallery.putExtra("Cast",favourite.get(position).getCast());
                gallery.putExtra("Director",favourite.get(position).getDirector());
                gallery.putExtra("Release date",favourite.get(position).getRelease_date());
                gallery.putExtra("Run time",favourite.get(position).getRun_time());
                gallery.putExtra("Language",favourite.get(position).getLanguage());
                gallery.putExtra("Overview",favourite.get(position).getOverview());
                gallery.putExtra("movies_id",favourite.get(position).getMovies_id());
                gallery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(gallery);
            }
        });
         holder.DeleteFavourite.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(final View v) {
                 String parameter = "remove-cart.php?id="+ favourite.get(position).getId() +"&otoken="+ Util.FAVOURITE_TOKEN +"&uid="+ Util.SESSION_USERID;
                 String url = ctx.getString(R.string.server_api_url) + parameter;
                 RequestQueue queue = Volley.newRequestQueue(ctx);
                 StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         try {
                             JSONObject obj = new JSONObject(response);
                             if (obj.getInt("status") == 200) {
                                 favourite.remove(position);
                                 notifyDataSetChanged();
                                 String MovieName = holder.film_name.getText().toString();
                                 Toast.makeText(ctx, MovieName + " removed from Favourite.", Toast.LENGTH_SHORT).show();
                             }
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
                 stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                 queue.add(stringRequest);
//                 final String MovieName;
//                 MovieName = holder.film_name.getText().toString();
//                 android.os.Handler handler = new Handler();
//                 handler.post(new Runnable() {
//                     @Override
//                     public void run() {
//                         String[] field = new String[1];
//                         field[0] = "Movie_name";
//
//                         String[] data = new String[1];
//                         data[0] = MovieName;
//
//                         PutData putData = new PutData("http://192.168.100.129:8080/ERA/DeleteFavouriteMovies.php", "POST", field, data);
//                         if (putData.startPut()) {
//                             if (putData.onComplete()) {
//                                 String result = putData.getResult();
//                                 if (result.equals("Successfully removed from Favourite")) {
//                                     holder.favourite_layout.setVisibility(View.GONE);
//                                     Intent favourite = new Intent(ctx, FavouriteFilm.class);
//                                     ctx.startActivity(favourite);
//                                     FavouriteFilm favouriteFilm = (FavouriteFilm) ctx;
//                                     favouriteFilm.FavouriteIcon.setVisibility(View.INVISIBLE);
//                                     favouriteFilm.FirstFavouriteText.setVisibility(View.INVISIBLE);
//                                     favouriteFilm.SecondFavouriteText.setVisibility(View.INVISIBLE);
//                                     favouriteFilm.overridePendingTransition(0,0);
//                                     favouriteFilm.finish();
//                                     Toast.makeText(ctx, MovieName + " removed from Favourite List", Toast.LENGTH_SHORT).show();
//                                 } else {
//                                     Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
//                                 }
//                             }
//                         }
//                     }
//                 });
             }
         });
    }

    @Override
    public int getItemCount() {
        return favourite.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView film_name, RunTime;
        ImageView film_image;
        TextView DeleteFavourite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            film_name = itemView.findViewById(R.id.film_text);
            RunTime = itemView.findViewById(R.id.RunTime);
            film_image = itemView.findViewById(R.id.film_image);
            DeleteFavourite = itemView.findViewById(R.id.DeleteFavourite);
        }
    }
}
