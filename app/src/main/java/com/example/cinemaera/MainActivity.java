package com.example.cinemaera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView main_recyclerView;
    MainAdapter mainAdapter;
    List<FilmCategoryName> filmCategoryNames = new ArrayList<>();
    List<Film> films = new ArrayList<>();
    List<Film> film2 = new ArrayList<>();
    List<Film> film3 = new ArrayList<>();
//    String url = getString(R.string.server_api_url) + "home.php";

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        FilmExtract();

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
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.account_bar:
                        Intent intent1 = new Intent(MainActivity.this,ProfileUser.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }
                return true;
            }
        });
    }
    private void setMain_recyclerView(List<FilmCategoryName> filmCategoryNames){
        main_recyclerView = findViewById(R.id.recycle_view_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,false);
        main_recyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter = new MainAdapter(this,filmCategoryNames);
        main_recyclerView.setAdapter(mainAdapter);

    }

    public void FilmExtract(){
        String url = getString(R.string.server_api_url) + "home.php";
        RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                filmCategoryNames.add(new FilmCategoryName(1,"Action movies",films));
                filmCategoryNames.add(new FilmCategoryName(2,"Love stories",film2));
                filmCategoryNames.add(new FilmCategoryName(3,"Horror movies",film3));
                setMain_recyclerView(filmCategoryNames);

                    try {
                        JSONArray jsonArray = response.getJSONArray("Action_movies");
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String Fid = jsonObject.getString("id");
                            String Fimg = jsonObject.getString("film image");
                            String Fname = jsonObject.getString("film name");
                            String Price = jsonObject.getString("Price");
                            String Tvideos = jsonObject.getString("trailer videos");
                            String Cast = jsonObject.getString("Cast");
                            String Director = jsonObject.getString("Director");
                            String Release_date = jsonObject.getString("Release_date");
                            String Run_time = jsonObject.getString("Run_time");
                            String Language = jsonObject.getString("Language");
                            String Overview = jsonObject.getString("Overview");
                            Film category1 = new Film(Fid,Fimg,Fname,Price,Tvideos,Cast, Director,Release_date,Run_time,Language,Overview);
                            films.add(category1);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                try {
                    JSONArray jsonArray = response.getJSONArray("Love_movies");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Fid = jsonObject.getString("id");
                        String Fimg = jsonObject.getString("film image");
                        String Fname = jsonObject.getString("film name");
                        String Price = jsonObject.getString("Price");
                        String Cast = jsonObject.getString("Cast");
                        String Director = jsonObject.getString("Director");
                        String Release_date = jsonObject.getString("Release_date");
                        String Run_time = jsonObject.getString("Run_time");
                        String Language = jsonObject.getString("Language");
                        String Overview = jsonObject.getString("Overview");
                        Film category2 = new Film(Fid,Fimg,Fname,Price,"fsf",Cast,Director,Release_date,Run_time,Language,Overview);
                        film2.add(category2);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = response.getJSONArray("Horror_movies");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Fid = jsonObject.getString("id");
                        String Fimg = jsonObject.getString("film image");
                        String Fname = jsonObject.getString("film name");
                        String Price = jsonObject.getString("Price");
                        String Cast = jsonObject.getString("Cast");
                        String Director = jsonObject.getString("Director");
                        String Release_date = jsonObject.getString("Release_date");
                        String Run_time = jsonObject.getString("Run_time");
                        String Language = jsonObject.getString("Language");
                        String Overview = jsonObject.getString("Overview");
                        Film category3 = new Film(Fid,Fimg,Fname,Price,"fdaff",Cast,Director,Release_date,Run_time,Language,Overview);
                        film3.add(category3);

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



