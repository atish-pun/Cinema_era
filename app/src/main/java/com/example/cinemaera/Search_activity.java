package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
    List<Film.Search> search_Film = new ArrayList<Film.Search>();
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
        String url = getString(R.string.server_api_url) + "home.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("Action_movies");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String Fname = obj.getString("film name");
                        String Fid = obj.getString("id");
                        Film.Search category1 = new Film.Search(Fname,Fid);
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
                        String Fname = obj.getString("film name");
                        String Fid = obj.getString("id");
                        Film.Search category1 = new Film.Search(Fname,Fid);
                        search_Film.add(category1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("Horror_movies");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String Fname = obj.getString("film name");
                        String Fid = obj.getString("id");
                        Film.Search category1 = new Film.Search(Fname,Fid);
                        search_Film.add(category1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Search_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}