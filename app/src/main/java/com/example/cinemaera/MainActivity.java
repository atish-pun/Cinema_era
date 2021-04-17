package com.example.cinemaera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView main_recyclerView;
    SliderView Movie_slider;
    MainAdapter mainAdapter;
    MovieSliderAdapter movieSliderAdapter;
    SwipeRefreshLayout MainActivityRefresh;
    List<FilmCategoryName> filmCategoryNames = new ArrayList<>();
    List<Film> films = new ArrayList<>();
    List<Film> film2 = new ArrayList<>();
    List<Film> film3 = new ArrayList<>();
    List<Film> movieSlider = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Movie_slider = findViewById(R.id.Movie_slider);
        MainActivityRefresh = findViewById(R.id.MainActivityRefresh);
        FilmExtract();
        MainActivityRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FilmExtract();
                MainActivityRefresh.setRefreshing(false);

            }
        });

        ImageView imageView = findViewById(R.id.search_custom);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(MainActivity.this, Search_activity.class);
                startActivity(search);
            }
        });


//                            For navigation bar
        BottomNavigationView nav = findViewById(R.id.buttom_nav);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.favourite_bar:
                        Intent intent = new Intent(MainActivity.this, FavouriteFilm.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.account_bar:
                        Intent intent1 = new Intent(MainActivity.this, ProfileUser.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    private void setMain_recyclerView(List<FilmCategoryName> filmCategoryNames) {
        main_recyclerView = findViewById(R.id.recycle_view_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        main_recyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter = new MainAdapter(this, filmCategoryNames);
        main_recyclerView.setAdapter(mainAdapter);

    }

    public void FilmExtract() {
        String url = getString(R.string.server_api_url) + "home.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        filmCategoryNames.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                filmCategoryNames.add(new FilmCategoryName(1,"Action movies",films));
                filmCategoryNames.add(new FilmCategoryName(2,"Love stories",film2));
                filmCategoryNames.add(new FilmCategoryName(3,"Horror movies",film3));
                setMain_recyclerView(filmCategoryNames);

                try {
                    JSONObject object = new JSONObject(response);
                    movieSlider.clear();
                    JSONArray jsonArray = object.getJSONArray("Slider_movies");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String Fid = obj.getString("id");
                        String Fimg = obj.getString("film image");
                        String Image_location = getString(R.string.Image_url) + Fimg;
                        String Fname = obj.getString("film name");
                        String Price = obj.getString("Price");
                        String Tvideos = obj.getString("trailer videos");
                        String Trailer_video = getString(R.string.Trailer_Video_url) + Tvideos;
                        String Cast = obj.getString("Cast");
                        String Director = obj.getString("Director");
                        String Release_date = obj.getString("Release_date");
                        String Run_time = obj.getString("Run_time");
                        String Language = obj.getString("Language");
                        String Overview = obj.getString("Overview");
                        Film SliderMovie = new Film(Fid, Image_location, Fname, Price, Trailer_video, Cast, Director, Release_date, Run_time, Language, Overview);
                        movieSlider.add(SliderMovie);
                        movieSliderAdapter = new MovieSliderAdapter(MainActivity.this,movieSlider);
                        Movie_slider.setSliderAdapter(movieSliderAdapter);
                        Movie_slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        Movie_slider.setIndicatorAnimation(IndicatorAnimationType.DROP);
                        Movie_slider.startAutoCycle();
                        Movie_slider.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject object = new JSONObject(response);
                    films.clear();
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
                            String Cast = obj.getString("Cast");
                            String Director = obj.getString("Director");
                            String Release_date = obj.getString("Release_date");
                            String Run_time = obj.getString("Run_time");
                            String Language = obj.getString("Language");
                            String Overview = obj.getString("Overview");
                            Film category1 = new Film(Fid, Image_location, Fname, Price, Trailer_video, Cast, Director, Release_date, Run_time, Language, Overview);
                            films.add(category1);
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject object = new JSONObject(response);
                    film2.clear();
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
                        String Cast = obj.getString("Cast");
                        String Director = obj.getString("Director");
                        String Release_date = obj.getString("Release_date");
                        String Run_time = obj.getString("Run_time");
                        String Language = obj.getString("Language");
                        String Overview = obj.getString("Overview");
                        Film category1 = new Film(Fid, Image_location, Fname, Price, Trailer_video, Cast, Director, Release_date, Run_time, Language, Overview);
                        film2.add(category1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject object = new JSONObject(response);
                    film3.clear();
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
                        String Cast = obj.getString("Cast");
                        String Director = obj.getString("Director");
                        String Release_date = obj.getString("Release_date");
                        String Run_time = obj.getString("Run_time");
                        String Language = obj.getString("Language");
                        String Overview = obj.getString("Overview");
                        Film category1 = new Film(Fid, Image_location, Fname, Price, Trailer_video, Cast, Director, Release_date, Run_time, Language, Overview);
                        film3.add(category1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }}





