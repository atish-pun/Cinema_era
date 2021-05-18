package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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

public class Search_activity extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ImageView SearchBack;
    List<Film> search_Film = new ArrayList<Film>();
    SearchAdapter searchAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        getSupportActionBar().hide();
        FilmExtract();
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.SearchList);
        SearchBack = findViewById(R.id.SearchBack);
        searchAdapter = new SearchAdapter(this,search_Film);
        listView.setAdapter(searchAdapter);

        int SearchUnderLine = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = findViewById(SearchUnderLine);
        searchPlate.setBackgroundColor(Color.parseColor("#1C1B1B"));

        SearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   finish();

            }
        });

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }
           @Override
           public boolean onQueryTextChange(String newText) {
               searchAdapter.getFilter().filter(newText);
               return false;
           }
       });

    }
    private void FilmExtract(){
        String url = getString(R.string.server_api_url) + "mainMovies.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("Action_movies");
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
                        search_Film.add(category1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                        Film category2 = new Film(Fid, Image_location, Fname, Price, Trailer_video, Full_movies, Cast, Director, Release_date, Run_time, Language, Overview);
                        search_Film.add(category2);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("Horror_movies");
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
                        Film category3 = new Film(Fid, Image_location, Fname, Price, Trailer_video, Full_movies, Cast, Director, Release_date, Run_time, Language, Overview);
                        search_Film.add(category3);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Search_activity.this, "Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}