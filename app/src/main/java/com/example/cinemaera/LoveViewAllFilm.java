package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoveViewAllFilm extends AppCompatActivity {
    RecyclerView ViewRecycler;
    List<Film> viewFilms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_film);
        ViewRecycler = findViewById(R.id.ViewRecycler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Love stories");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        ViewRecycler.setLayoutManager(gridLayoutManager);
        ViewExtract();
    }
    public void ViewExtract() {
        String url = getString(R.string.server_api_url) + "mainMovies.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("Love_stories");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String Fid = obj.getString("id");
                        String Fimg = obj.getString("film image");
                        String Image_location = getString(R.string.Image_url) + Fimg;
                        String Fname = obj.getString("film name");
                        String Price = obj.getString("Price");
                        String Tvideos = obj.getString("trailer videos");
                        String Trailer_video = getString(R.string.Trailer_Video_url) + Tvideos;
                        String FMovies = obj.getString("Full_movies");
                        String Full_movies = getString(R.string.Full_movie_url) + FMovies;
                        String Cast = obj.getString("Cast");
                        String Director = obj.getString("Director");
                        String Release_date = obj.getString("Release_date");
                        String Run_time = obj.getString("Run_time");
                        String Language = obj.getString("Language");
                        String Overview = obj.getString("Overview");
                        Film category1 = new Film(Fid, Image_location, Fname, Price, Trailer_video, Full_movies, Cast, Director, Release_date, Run_time, Language, Overview);
                        viewFilms.add(category1);
                        ViewAllFilmAdapter viewAdapter = new ViewAllFilmAdapter(LoveViewAllFilm.this,viewFilms);
                        ViewRecycler.setAdapter(viewAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoveViewAllFilm.this, "Sever is in Maintenance!!", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}