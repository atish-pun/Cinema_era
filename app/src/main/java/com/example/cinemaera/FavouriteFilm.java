package com.example.cinemaera;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FavouriteFilm extends AppCompatActivity {
    RecyclerView favourite_recycle;
    FavouriteAdapter favouriteAdapter;
    List<Film.FavouriteInfo> favourite = new ArrayList<>();
    ImageView FavouriteIcon;
    TextView FirstFavouriteText, SecondFavouriteText;
    SwipeRefreshLayout swipeRefreshLayout;
    float ratingValue;
    String TotalRatings;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavouriteFilm.this,LinearLayoutManager.VERTICAL,false);
        favourite_recycle.setLayoutManager(linearLayoutManager);
        Navigation();
        ExtractFavouriteMovies();
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
                Intent search = new Intent(FavouriteFilm.this, Search_activity.class);
                startActivity(search);
                overridePendingTransition(0, 0);
                break;

            case R.id.setting_bar:
                Intent setting = new Intent(FavouriteFilm.this, SettingForm.class);
                startActivity(setting);
                overridePendingTransition(0, 0);
                break;

            case R.id.share_bar:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String app = "Download this Cinema Era app through:-http://lalpun.com.np/=en";
                intent.putExtra(Intent.EXTRA_SUBJECT,app);
                startActivity(Intent.createChooser(intent,"ShareVia"));
                break;

            case R.id.feedback_bar:
                FeedbackHelp();
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    public void FeedbackHelp (){
        AlertDialog.Builder alert = new AlertDialog.Builder(FavouriteFilm.this);
        View view1 = getLayoutInflater().inflate(R.layout.feedback_help, null);
        final EditText review_txt = view1.findViewById(R.id.review_type);
        final Button cancel = view1.findViewById(R.id.cancel);
        final Button submit = view1.findViewById(R.id.submit);
        TextView userEmail = view1.findViewById(R.id.UserName);
        final TextView ratevalue = view1.findViewById(R.id.ratevalue);
        final RatingBar ratebar = view1.findViewById(R.id.review_ratingbar);
        userEmail.setText("From : " + Util.SESSION_EMAIL);

        alert.setView(view1);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue = ratebar.getRating();
                if (ratingValue <= 1 && ratingValue > 0)
                    ratevalue.setText(ratingValue + "");
                else if (ratingValue <= 2 && ratingValue > 1)
                    ratevalue.setText(ratingValue + "");
                else if (ratingValue <= 3 && ratingValue > 2)
                    ratevalue.setText(ratingValue + "");
                else if (ratingValue <= 4 && ratingValue > 3)
                    ratevalue.setText(ratingValue + "");
                else if (ratingValue <= 5 && ratingValue > 4)
                    ratevalue.setText(ratingValue + "");

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ReviewOutput;
                TotalRatings = ratevalue.getText().toString();
                ReviewOutput = review_txt.getText().toString();
                if (!TotalRatings.equals("") || !ReviewOutput.equals("")){
                    String url = getString(R.string.server_api_url) + "FeedBack_and_help.php?email=" + Util.SESSION_EMAIL + "&reviews=" + ReviewOutput + "&ratedValue=" + TotalRatings;
                    RequestQueue queue = Volley.newRequestQueue(FavouriteFilm.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getInt("status") == 200) {
                                    Toast.makeText(FavouriteFilm.this, object.getString("content"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(FavouriteFilm.this, "Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(stringRequest);
                    review_txt.setText("");
                    ratebar.setRating(0);
                    ratevalue.setText("");
                    alertDialog.dismiss();

                }
                else {
                    Toast.makeText(FavouriteFilm.this,"Please fill up at least one of the field and submit",Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.show();
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
        String url = getString(R.string.server_api_url) + "FavouriteList.php?otoken=" + Util.FAVOURITE_TOKEN + "&uid=" + Util.SESSION_USERID;
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
                            String movies_id = obj.getString("movies_id");
                            String A_name = obj.getString("A_name");
                            String Price = obj.getString("Price");
                            String A_poster = obj.getString("A_poster");
                            String Image_location = getString(R.string.Image_url) + A_poster;
                            String Trailer_videos = obj.getString("Trailer_videos");
                            String Trailer_video = getString(R.string.Trailer_Video_url) + Trailer_videos;
                            String FMovies = obj.getString("Full_movies");
                            String Full_movies = getString(R.string.Full_movie_url) + FMovies;
                            String Cast = obj.getString("Cast");
                            String Director = obj.getString("Director");
                            String Release_date = obj.getString("Release_date");
                            String Run_time = obj.getString("Run_time");
                            String Language = obj.getString("Language");
                            String Overview = obj.getString("Overview");
                            Film.FavouriteInfo favouriteInfo = new Film.FavouriteInfo(id,movies_id,A_name,Price, Image_location,Trailer_video, Full_movies, Cast,Director,Release_date,Run_time,Language,Overview);
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
                Toast.makeText(FavouriteFilm.this,"Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }
}