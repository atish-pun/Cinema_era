package com.example.cinemaera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khalti.checkout.api.KhaltiApi;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.ErrorListener;

public class Film_gallery extends AppCompatActivity {
    ImageView filmImg,trailer_img,gradientImg, backTrailer;
    VideoView trailer_video;
    TextView filmTxt, startTime, endTime, cast, director, releaseDate, runtime, language,overview,Price,AvgRatings;
    Button frontTrailerPlay, innerTrailerPlay,fav;
    SeekBar seekBar;
    View TrailerBarLoad;
    float ratingValue;
    String TotalRatings, Film_name,MoviePoster,Trailer_videos, Full_movies, Movie_id, Costs;
    ConstraintLayout videoConstraint;
    List<Film.ReviewInfo> reviewInfo = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout FilmGalleryRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_gallery);
        getSupportActionBar().hide();
        filmImg = findViewById(R.id.inner_Fimg);
        trailer_video = findViewById(R.id.trailer_video);
        filmTxt = findViewById(R.id.inner_Fname);
        seekBar = findViewById(R.id.seekBar);
        trailer_img = findViewById(R.id.trailer_img);
        gradientImg = findViewById(R.id.gradientimg);
        frontTrailerPlay = findViewById(R.id.fronttrailerplay);
        innerTrailerPlay = findViewById(R.id.innerTrialerplay);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        backTrailer = findViewById(R.id.backTrailer);
        videoConstraint = findViewById(R.id.videoConstrant);
        cast = findViewById(R.id.castTxt);
        director = findViewById(R.id.directorTxt);
        releaseDate = findViewById(R.id.dateTxt);
        runtime = findViewById(R.id.timeTxt);
        language = findViewById(R.id.languageTxt);
        overview = findViewById(R.id.OverviewTxt);
        fav = findViewById(R.id.favourite);
        Price = findViewById(R.id.price);
        TrailerBarLoad = findViewById(R.id.TrailerBarLoad);
        AvgRatings =  findViewById(R.id.AvgRatings);
        FilmGalleryRefresh = findViewById(R.id.FilmGalleryRefresh);
        MoviePoster = getIntent().getStringExtra("Film images");
        Film_name = getIntent().getStringExtra("Film names");
        Movie_id = getIntent().getStringExtra("movies_id");
        Costs = getIntent().getStringExtra("Price");
        filmTxt.setText(Film_name);
        Picasso.get().load(MoviePoster).into(filmImg);
        Picasso.get().load(MoviePoster).into(trailer_img);
        Price.setText("Price: Rs " + Costs);
        Trailer_videos = getIntent().getStringExtra("Trailer video");
        trailer_video.setVideoURI(Uri.parse(Trailer_videos));
        Full_movies = getIntent().getStringExtra("Full movies");
        cast.setText(getIntent().getStringExtra("Cast"));
        director.setText(getIntent().getStringExtra("Director"));
        releaseDate.setText(getIntent().getStringExtra("Release date"));
        runtime.setText(getIntent().getStringExtra("Run time"));
        language.setText(getIntent().getStringExtra("Language"));
        overview.setText(getIntent().getStringExtra("Overview"));
        endTime.setVisibility(View.INVISIBLE);
        startTime.setVisibility(View.INVISIBLE);
        seekBar.setVisibility(View.INVISIBLE);
        innerTrailerPlay.setVisibility(View.INVISIBLE);
        linearLayoutManager = new LinearLayoutManager(Film_gallery.this,LinearLayoutManager.VERTICAL,false);
        setHandler();
        InitSeekBar();
        TrailerVideoCLick();
        ExtractReviews();
        AverageRatings();

        FilmGalleryRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ExtractReviews();
                AverageRatings();
                FilmGalleryRefresh.setRefreshing(false);
            }
        });
    }

    public void setHandler(){
        final Handler moviehandler = new Handler();
        Runnable movierun = new Runnable() {
        @Override
        public void run() {
                if (trailer_video.getCurrentPosition() > 0){
                    int currentTime = trailer_video.getCurrentPosition();
                    seekBar.setProgress(currentTime);
                    startTime.setText(convertIntoTime(currentTime));
                    endTime.setText(convertIntoTime(trailer_video.getDuration()));
            }
            moviehandler.postDelayed(this,0);
        }
    };
    moviehandler.postDelayed(movierun,500);
}

    private String convertIntoTime(int millisecond){
        String Time;
        int x,sec,min,hrs;
        x = (millisecond/1000);
        sec = x % 60;
        x /= 60;
        min = x % 60;
        x /= 60;
        hrs = x % 60;
        if (hrs != 0)
            Time = String.format("%02d", hrs)+":"+String.format("%02d", min)+":"+String.format("%02d",sec);
        else Time = String.format(String.format("%02d", min)+":"+String.format("%02d",sec));
        return Time;
}

    public void InitSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()){
                    case R.id.seekBar:
                        if (fromUser){
                            trailer_video.seekTo(progress);
                            int currentTime = trailer_video.getCurrentPosition();
                            startTime.setText(convertIntoTime(currentTime));
                            endTime.setText(convertIntoTime(trailer_video.getDuration()));
                        }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
}

    public void fronttrailerplay(View view) {
        trailer_img.setVisibility(view.GONE);
        gradientImg.setVisibility(view.INVISIBLE);
        frontTrailerPlay.setVisibility(view.INVISIBLE);
        videoConstraint.setVisibility(View.VISIBLE);
        trailer_video.setVisibility(View.VISIBLE);
        trailer_video.start();
        trailer_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                TrailerBarLoad.setVisibility(View.GONE);
                seekBar.setMax(trailer_video.getDuration());
                endTime.setVisibility(View.VISIBLE);
                startTime.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                innerTrailerPlay.setVisibility(View.VISIBLE);
            }
        });

        trailer_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_play));
            }
        });

        trailer_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                TrailerBarLoad.setVisibility(View.GONE);
                endTime.setVisibility(View.INVISIBLE);
                startTime.setVisibility(View.INVISIBLE);
                seekBar.setVisibility(View.INVISIBLE);
                innerTrailerPlay.setVisibility(View.INVISIBLE);
                AlertDialog.Builder alert = new AlertDialog.Builder(Film_gallery.this,R.style.Alert);
                alert.setTitle("Can't play Video");
                alert.setMessage("Trailer video of "+Film_name+" isn't available!!");
                alert.setPositiveButton("OK",null);
                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                return true;
            }
        });
    }

    public void innerTrialerplay(View view) {
        if (trailer_video.isPlaying()) {
            trailer_video.pause();
            innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_play));
        }
        else {
            trailer_video.start();
            seekBar.setMax(trailer_video.getDuration());
            innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_pause));
        }
    }

    public void Backtrailer(View view) {
        trailer_img.setVisibility(view.VISIBLE);
        gradientImg.setVisibility(view.VISIBLE);
        frontTrailerPlay.setVisibility(view.VISIBLE);
        innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_pause));
        videoConstraint.setVisibility(View.GONE);
        trailer_video.setVisibility(View.INVISIBLE);
    }

    public void TrailerVideoCLick() {
        trailer_video.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View v) {
                visible = !visible;
                TransitionManager.beginDelayedTransition(videoConstraint);
                innerTrailerPlay.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
                endTime.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
                startTime.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
                seekBar.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
                backTrailer.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }

        public void review_dialoge (View view){
            AlertDialog.Builder alert = new AlertDialog.Builder(Film_gallery.this);
            View view1 = getLayoutInflater().inflate(R.layout.review_layout, null);
            final EditText review_txt = view1.findViewById(R.id.review_type);
            final Button cancel = view1.findViewById(R.id.cancel);
            final Button submit = view1.findViewById(R.id.submit);
            final TextView ratevalue = view1.findViewById(R.id.ratevalue);
            final RatingBar ratebar = view1.findViewById(R.id.review_ratingbar);
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
                        if(Util.FAVOURITE_TOKEN == null || Util.FAVOURITE_TOKEN.equals("")) Util.GenerateFavouriteToken(Film_gallery.this);
                        String url = getString(R.string.server_api_url) + "add-reviews.php?uid=" + Util.SESSION_USERID + "&pid=" + Movie_id +"&otoken=" + Util.FAVOURITE_TOKEN + "&reviews=" + ReviewOutput + "&ratedValue=" + TotalRatings;
                        RequestQueue queue = Volley.newRequestQueue(Film_gallery.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    if (object.getInt("status") == 200) {
                                        Toast.makeText(Film_gallery.this, object.getString("content"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Film_gallery.this,"Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Film_gallery.this,"Please fill up at least one of the field and submit",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            alertDialog.show();
        }
    public  void ExtractReviews(){
        String url = getString(R.string.server_api_url) + "review-list.php?Movies_id="+ Movie_id;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    reviewInfo.clear();
                    JSONArray jsonArray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String ReviewId = obj.getString("ReviewId");
                        String reviews = obj.getString("reviews");
                        Float RatedValue = Float.valueOf(obj.getString("RatedValue"));
                        String fullName = obj.getString("fullName");
                        Film.ReviewInfo reviewInfo1 = new Film.ReviewInfo(ReviewId,reviews, RatedValue, fullName);
                        reviewInfo.add(reviewInfo1);
                        Review_adapter review_adapter = new Review_adapter(Film_gallery.this, reviewInfo);
                        RecyclerView reviewRecycler = findViewById(R.id.ReviewRecycler);
                        reviewRecycler.setAdapter(review_adapter);
                        reviewRecycler.setLayoutManager(linearLayoutManager);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
            new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Film_gallery.this, "Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    public void FavouriteBtn(View view) {
        if(Util.FAVOURITE_TOKEN == null || Util.FAVOURITE_TOKEN.equals("")) Util.GenerateFavouriteToken(this);
        String url = getString(R.string.server_api_url) + "FavouriteAdd.php?pid=" + Movie_id + "&otoken=" + Util.FAVOURITE_TOKEN + "&uid=" + Util.SESSION_USERID;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("status") == 200) {
                        Toast.makeText(Film_gallery.this,object.getString("content"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Film_gallery.this, "Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void BuyOrWatchFilms(View view) {
        if(Util.FAVOURITE_TOKEN == null || Util.FAVOURITE_TOKEN.equals("")) Util.GenerateFavouriteToken(Film_gallery.this);
        String url = getString(R.string.server_api_url) + "Khalti_status.php?pid=" + Movie_id + "&uid=" + Util.SESSION_USERID +"&otoken=" + Util.FAVOURITE_TOKEN ;
        RequestQueue queue = Volley.newRequestQueue(Film_gallery.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("status") == 200) {
                        Intent intent = new Intent(Film_gallery.this, Movies.class);
                        intent.putExtra("Full_movie",Full_movies);
                        intent.putExtra("Film names",Film_name);
                        startActivity(intent);
                    }
                    else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Film_gallery.this);
                        View view1 = getLayoutInflater().inflate(R.layout.payment_alert_box, null);
                        final TextView paymentCost = view1.findViewById(R.id.Cost);
                        ImageView filmImage = view1.findViewById(R.id.filmImage);
                        TextView FilmName = view1.findViewById(R.id.FilmName);
                        FilmName.setText(Film_name);
                        Picasso.get().load(MoviePoster).into(filmImage);
                        final ImageView cancel = view1.findViewById(R.id.cancel);
                        KhaltiButton Khalti = view1.findViewById(R.id.khalti_button);
                        paymentCost.setText("The Total cost: Rs "+ Costs);
                        alert.setView(view1);

                        final AlertDialog alertDialog = alert.create();
                        alertDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
                        alertDialog.setCanceledOnTouchOutside(false);

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        Map<String, Object> map = new HashMap<>();
                        Config.Builder builder = new Config.Builder("test_public_key_60306ba0ad9645d0a4b0f7bbc71d846d", Movie_id, Film_name, Long.parseLong(Costs)*100, new OnCheckOutListener() {
                            @Override
                            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                                Toast.makeText(Film_gallery.this, errorMap.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(@NonNull Map<String, Object> data) {
                                JSONObject jsonObject = new JSONObject(data);
                                try {
                                    String token = jsonObject.getString("token");
                                    Double amount = jsonObject.getDouble("amount");
                                    Khalti_Verification(token, amount);
                                    alertDialog.dismiss();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                                    add(PaymentPreference.KHALTI);
                                }});
                        Config config = builder.build();
                        Khalti.setCheckOutConfig(config);
                    }
                } catch (JSONException e) {
                    Toast.makeText(Film_gallery.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Film_gallery.this,"Slow Internet Connection Detected!!", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    public void Khalti_Verification(String token, Double amount) {
        RequestQueue queue = Volley.newRequestQueue(Film_gallery.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://khalti.com/api/v2/payment/verify/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                KhaltiPay(response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Film_gallery.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "key test_secret_key_cb7b90d18fc7480f82a5fd712424520d");
                return headers;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> payload = new HashMap<String, String>();
                payload.put("token", token);
                payload.put("amount", amount.toString());
                return payload;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void KhaltiPay(String SuccessfulTransaction) {
        String url = getString(R.string.server_api_url) + "add-Khalti.php?pid=" + Movie_id + "&uid=" + Util.SESSION_USERID +"&otoken=" + Util.FAVOURITE_TOKEN + "&transaction=" + SuccessfulTransaction;
        RequestQueue queue = Volley.newRequestQueue(Film_gallery.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("status") == 200) {
                        Toast.makeText(Film_gallery.this,object.getString("content"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Film_gallery.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Film_gallery.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


    public void AverageRatings(){
        String url = getString(R.string.server_api_url) + "AvgRate.php?Movies_id=" + Movie_id;
        RequestQueue requestQueue = Volley.newRequestQueue(Film_gallery.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("content");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String AverageRating = jsonObject.getString("RatingAvg");
                        AvgRatings.setText(AverageRating);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Film_gallery", error.getMessage());
            }
        });
         stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         requestQueue.add(stringRequest);
     }

}



