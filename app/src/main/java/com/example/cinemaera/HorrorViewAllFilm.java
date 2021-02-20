package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HorrorViewAllFilm extends AppCompatActivity {
    RecyclerView ViewRecycler;
    List<Film> viewFilms = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_film);
        ViewRecycler = findViewById(R.id.ViewRecycler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Horror movies");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        ViewRecycler.setLayoutManager(gridLayoutManager);
        ViewExtract();
    }
    public void ViewExtract() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.100.129:8080/ERA/MoviesDetails.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("horror");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Fimg = jsonObject.getString("film image");
                        String Fname = jsonObject.getString("film name");
                        String Tvideos = jsonObject.getString("trailer videos");
                        String Cast = jsonObject.getString("Cast");
                        String Director = jsonObject.getString("Director");
                        String Release_date = jsonObject.getString("Release_date");
                        String Run_time = jsonObject.getString("Run_time");
                        String Language = jsonObject.getString("Language");
                        String Overview = jsonObject.getString("Overview");
                        Film actionMovies = new Film(Fimg, Fname, Tvideos, Cast, Director, Release_date, Run_time, Language, Overview);
                        viewFilms.add(actionMovies);
                        ViewAllFilmAdapter viewAdapter = new ViewAllFilmAdapter(HorrorViewAllFilm.this,viewFilms);
                        ViewRecycler.setAdapter(viewAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
}