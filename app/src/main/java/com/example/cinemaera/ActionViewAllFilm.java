package com.example.cinemaera;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ActionViewAllFilm extends AppCompatActivity {
    RecyclerView ViewRecycler;
    boolean b = false;
    List<Film> viewFilms = new ArrayList<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_film);
        ViewRecycler = findViewById(R.id.ViewRecycler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Action movies");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        ViewRecycler.setLayoutManager(gridLayoutManager);
        ViewExtract();



//        KhaltiPayment();
    }
        public void ViewExtract() {
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.100.129:8080/ERA/api/home.php", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
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
                            Film actionMovies = new Film(Fid,Fimg, Fname,Price, Tvideos, Cast, Director, Release_date, Run_time, Language, Overview);
                            viewFilms.add(actionMovies);
                            ViewAllFilmAdapter viewAdapter = new ViewAllFilmAdapter(ActionViewAllFilm.this,viewFilms);
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

//        public void KhaltiPayment(){
//            Map<String, Object> map = new HashMap<>();
//            map.put("merchant_extra", "This is extra data");
//            Config.Builder builder = new Config.Builder("test_public_key_60306ba0ad9645d0a4b0f7bbc71d846d", "Product ID", "test for payment", 3000L, new OnCheckOutListener() {
//                @Override
//                public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
//                    Log.i(action, errorMap.toString());
//                }
//
//                @Override
//                public void onSuccess(@NonNull Map<String, Object> data) {
//                    Log.i("Payment successfully", data.toString());
//                }
//            })
//                    .paymentPreferences(new ArrayList<PaymentPreference>() {{
//                        add(PaymentPreference.KHALTI);
//                    }})
//                    .additionalData(map)
//                    .productUrl("");
//            Config config = builder.build();
//            final KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(this, config);
//            khaltibutton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (b == true ){
//                        khaltibutton.setVisibility(View.GONE);
//                        movie.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        khaltiCheckOut.show();
//                        b = true;
//                    }
//                }
//            });
//        }
        }


