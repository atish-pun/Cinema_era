package com.example.cinemaera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileUser extends AppCompatActivity {
    TextView emailText, nameText,TotalReview;
    RecyclerView ViewRecycler;
    Button StoreProfileImage;
    ImageView profile_image, UploadProfilePhoto, ProfileUpdate;
    Bitmap bitmap;
    String encodedImage;
    List<Film.TransactedMovieInfo> transactedMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        emailText = findViewById(R.id.email);
        nameText = findViewById(R.id.name);
        ProfileUpdate =  findViewById(R.id.ProfileUpdate);
        profile_image = findViewById(R.id.profile_image);
        ViewRecycler = findViewById(R.id.ViewRecycler);
        UploadProfilePhoto = findViewById(R.id.UploadProfilePhoto);
        StoreProfileImage = findViewById(R.id.StoreProfileImage);
        TotalReview =  findViewById(R.id.TotalReview);
        getSupportActionBar().setTitle("Account Profile");
        emailText.setText(Util.GetValue(getApplicationContext(), "Cinemapref_email"));
        nameText.setText("User name: "+ Util.GetValue(getApplicationContext(),"Cinemapref_name"));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3, RecyclerView.VERTICAL,false);
        ViewRecycler.setLayoutManager(gridLayoutManager);
        ExtractTransactedMovies();
        TotalReviews();

        UploadProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ProfileUser.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        StoreProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.server_api_url) + "profile_image?upload=" + encodedImage;
                RequestQueue queue = Volley.newRequestQueue(ProfileUser.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    try {
                        JSONObject obj = new JSONObject(response);

                            Toast.makeText(ProfileUser.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    Toast.makeText(ProfileUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                });
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);
            }
        });

        ProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ProfileUser.this,Profile_Update.class);
                startActivity(intent);
            }
        });

        BottomNavigationView nav = findViewById(R.id.buttom_nav);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_bar:
                        Intent intent = new Intent(ProfileUser.this,MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.favourite_bar:
                        Intent intent1 = new Intent(ProfileUser.this,FavouriteFilm.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1  && resultCode == RESULT_OK && data!=null){
            Uri profilePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(profilePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profile_image.setImageBitmap(bitmap);

                imageSave(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageSave(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.setting_bar:

            case R.id.share_bar:

            case R.id.feedback_bar:

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void TotalReviews(){
        String url = getString(R.string.server_api_url) + "Total_Reviews.php?User_Id=" + Util.SESSION_USERID + "&token=" + Util.FAVOURITE_TOKEN;
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileUser.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("content");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Reviews = jsonObject.getString("TotalRate");
                        TotalReview.setText("Total reviews: " + Reviews);
                    }
                } catch (JSONException e) {
                    Log.d("ProfileUser", e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ProfileUser", error.getMessage());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void ExtractTransactedMovies(){
        String url = getString(R.string.server_api_url) + "transacted_movies_list.php?otoken=" + Util.FAVOURITE_TOKEN + "&uid=" + Util.SESSION_USERID;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
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
                            Film.TransactedMovieInfo transactedMovieInfo = new Film.TransactedMovieInfo(id,movies_id,A_name,Price, Image_location,Trailer_video, Full_movies,Cast,Director,Release_date,Run_time,Language,Overview);
                            transactedMovies.add(transactedMovieInfo);
                            Movies_transaction_adpater movies_transaction_adpater = new Movies_transaction_adpater(ProfileUser.this,transactedMovies);
                            ViewRecycler.setAdapter(movies_transaction_adpater);
                        }
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileUser.this,"Sever is in Maintenance!!", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    public void Logout(View view) {
        Util.SESSION_KEY = null;
        Util.SESSION_USERID = null;
        Util.SESSION_NAME = null;
        Util.SESSION_EMAIL = null;
        Util.ClearAllSharedPreference(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), AccountLogin.class);
        startActivity(intent);
        finish();

    }

}