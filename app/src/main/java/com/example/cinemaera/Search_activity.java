package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

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

public class Search_activity extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    List<Film> search_Film = new ArrayList<Film>();
    SearchAdapter searchAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
        FilmExtract();
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.SearchList);
        searchAdapter = new SearchAdapter(this,search_Film);
        listView.setAdapter(searchAdapter);

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
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.100.129:8080/ERA/MoviesDetails.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("action");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int Fid = jsonObject.getInt("id");
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
                        search_Film.add(category1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = response.getJSONArray("love");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int Fid = jsonObject.getInt("id");
                        String Fimg = jsonObject.getString("film image");
                        String Fname = jsonObject.getString("film name");
                        String Price = jsonObject.getString("Price");
                        String Cast = jsonObject.getString("Cast");
                        String Director = jsonObject.getString("Director");
                        String Release_date = jsonObject.getString("Release_date");
                        String Run_time = jsonObject.getString("Run_time");
                        String Language = jsonObject.getString("Language");
                        String Overview = jsonObject.getString("Overview");
                        Film category2 = new Film(Fid,Fimg,Fname,Price,"faf",Cast,Director,Release_date,Run_time,Language,Overview);
                        search_Film.add(category2);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = response.getJSONArray("horror");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int Fid = jsonObject.getInt("id");
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
                        search_Film.add(category3);

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