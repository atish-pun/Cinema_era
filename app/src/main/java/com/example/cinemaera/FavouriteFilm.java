package com.example.cinemaera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFilm extends AppCompatActivity {
    RecyclerView favourite_recycle;
    FavouriteAdapter favouriteAdapter;
    List<Film.FavouriteInfo> favourite = new ArrayList<>();
    List<Film.FavReview> favReviews = new ArrayList<>();
    ImageView FavouriteIcon;
    TextView FirstFavouriteText, SecondFavouriteText;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_film);
        favourite_recycle = findViewById(R.id.favourite_recycle);
        FavouriteIcon = findViewById(R.id.FavouriteIcon);
        FirstFavouriteText = findViewById(R.id.FirstFavouriteText);
        SecondFavouriteText = findViewById(R.id.SecondFavouriteText);
        swipeRefreshLayout = findViewById(R.id.RefreshFavourite);
        getSupportActionBar().setTitle("Favourites");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavouriteFilm.this,LinearLayoutManager.VERTICAL,false);
        favourite_recycle.setLayoutManager(linearLayoutManager);
        Navigation();
        ExtractFavouriteMovies();
        ExtractReviews();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ExtractFavouriteMovies();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bars,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search_bar:

            case R.id.setting_bar:

            case R.id.share_bar:

            case R.id.feedback_bar:

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void Navigation(){
        BottomNavigationView nav = findViewById(R.id.buttom_nav);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_bar:
                        Intent intent = new Intent(FavouriteFilm.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.account_bar:
                        Intent intent2 = new Intent(FavouriteFilm.this,ProfileUser.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }
                return true;
            }
        });
    }
    public void ExtractFavouriteMovies(){
        String url = getString(R.string.server_api_url) + "cart-list.php?otoken=" + Util.FAVOURITE_TOKEN + "&uid=" + Util.SESSION_USERID;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    favourite.clear();
                    if (object.getInt("status") == 200) {
                        JSONArray jsonArray = object.getJSONArray("content");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String id = obj.getString("id");
                            String A_name = obj.getString("A_name");
                            String A_poster = obj.getString("A_poster");
                            String Trailer_videos = obj.getString("Trailer_videos");
                            String Cast = obj.getString("Cast");
                            String Director = obj.getString("Director");
                            String Release_date = obj.getString("Release_date");
                            String Run_time = obj.getString("Run_time");
                            String Language = obj.getString("Language");
                            String Overview = obj.getString("Overview");
                            Film.FavouriteInfo favouriteInfo = new Film.FavouriteInfo(id,A_name, A_poster,Trailer_videos,Cast,Director,Release_date,Run_time,Language,Overview);
                            favourite.add(favouriteInfo);
                            favouriteAdapter = new FavouriteAdapter(FavouriteFilm.this, favourite);
                            favourite_recycle.setAdapter(favouriteAdapter);
                            FavouriteIcon.setVisibility(View.GONE);
                            FirstFavouriteText.setVisibility(View.GONE);
                            SecondFavouriteText.setVisibility(View.GONE);
                        }
                    } else {
                        FavouriteIcon.setVisibility(View.VISIBLE);
                        FirstFavouriteText.setVisibility(View.VISIBLE);
                        SecondFavouriteText.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FavouriteFilm.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }
    public  void ExtractReviews(){
        String url = getString(R.string.server_api_url) + "API_reviewFav";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String reviews = obj.getString("reviews");
                        String fullName = obj.getString("fullName");
                        Film.FavReview favReview = new Film.FavReview(reviews,fullName);
                        favReviews.add(favReview);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FavouriteFilm.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.100.129:8080/ERA/FavouriteDisplay.php", null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        String Fid = jsonObject.getString("id");
//                        String Fimg = jsonObject.getString("film image");
//                        String Fname = jsonObject.getString("film name");
//                        String Tvideos = jsonObject.getString("trailer videos");
//                        String Cast = jsonObject.getString("Cast");
//                        String Director = jsonObject.getString("Director");
//                        String Release_date = jsonObject.getString("Release_date");
//                        String Run_time = jsonObject.getString("Run_time");
//                        String Language = jsonObject.getString("Language");
//                        String Overview = jsonObject.getString("Overview");
//                        Film FavouriteMovies = new Film(Fid,Fimg, Fname, Tvideos, Cast, Director, Release_date, Run_time, Language, Overview);
//                        favourite.add(FavouriteMovies);
//                        favouriteAdapter = new FavouriteAdapter(FavouriteFilm.this,favourite);
//                        favourite_recycle.setAdapter(favouriteAdapter);
//                        FavouriteIcon.setVisibility(View.INVISIBLE);
//                        FirstFavouriteText.setVisibility(View.INVISIBLE);
//                        SecondFavouriteText.setVisibility(View.INVISIBLE);
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue.add(jsonArrayRequest);
//
//    }
}