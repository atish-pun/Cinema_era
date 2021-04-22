    package com.example.cinemaera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutputStream;

public class AccountLogin extends AppCompatActivity {
    private EditText emailL, passwordL;
    private TextView SignUp;
    private Button log_in;
    TextInputLayout EmailErrorTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);
        emailL = findViewById(R.id.editEmailAddress);
        passwordL = findViewById(R.id.editPassword);
        log_in = findViewById(R.id.button_login);
        SignUp = findViewById(R.id.signup);
        EmailErrorTxt = findViewById(R.id.EmailErrorTxt);
        getSupportActionBar().hide();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountLogin.this,AccountSignup.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String str_emailL, str_passwordL, str_EmailErrorTxt;
                str_emailL = emailL.getText().toString().trim();
                str_passwordL = passwordL.getText().toString().trim();

                if(!str_emailL.equals("") && !str_passwordL.equals("")){
                    if (Patterns.EMAIL_ADDRESS.matcher(str_emailL).matches())
                    {
                        String url = getString(R.string.server_api_url) + "login.php?emailAddress="+str_emailL+"&Password="+str_passwordL;
                        RequestQueue queue = Volley.newRequestQueue(AccountLogin.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj.getInt("status") == 200 ) {

                                        String session = obj.getString("session"), id = obj.getString("id"), fullName = obj.getString("fullName"), emailAddress = obj.getString("emailAddress"), favourite_token = obj.getString("Favourite_token");
                                        Util.SetKey(getApplicationContext(), "Cinemapref_session", session);
                                        Util.SESSION_KEY = session;
                                        Util.SetKey(getApplicationContext(), "Cinemapref_uid", id);
                                        Util.SESSION_USERID = id;
                                        Util.SetKey(getApplicationContext(), "Cinemapref_name", fullName);
                                        Util.SESSION_NAME = fullName;
                                        Util.SetKey(getApplicationContext(), "Cinemapref_email", emailAddress);
                                        Util.SESSION_EMAIL = emailAddress;

                                        if (favourite_token == null || favourite_token.equals("") || favourite_token.equals("null")) {
                                            Util.SetKey(getApplicationContext(), "Cinemapref_favourite_token", "");
                                            Util.FAVOURITE_TOKEN = null;
                                        } else {
                                            Util.SetKey(getApplicationContext(), "Cinemapref_favourite_token", favourite_token);
                                            Util.FAVOURITE_TOKEN = favourite_token;
                                        }
                                        Toast.makeText(AccountLogin.this,"Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        finish();


                                    } else {
                                        Toast.makeText(AccountLogin.this, obj.getString("content"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(AccountLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AccountLogin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        });
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        queue.add(stringRequest);
                    }
                    else{
                        Toast.makeText(AccountLogin.this, "Enter valid Email!!", Toast.LENGTH_SHORT).show();
                        EmailErrorTxt.setError("Invalid Email");
                    }
                }
                else{
                    Toast.makeText(AccountLogin.this, "Fields can't be empty!!", Toast.LENGTH_SHORT).show();
                }
            }

//                if( !str_emailL.equals("") && !str_passwordL.equals("")){
//                    Handler handler = new Handler();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Creating array for parameters
//                            String[] field = new String[2];
//                            field[0] = "emailAddress";
//                            field[1] = "Password";
//
//                            //Creating array for data
//                            String[] data = new String[2];
//                            data[0] = str_emailL;
//                            data[1] = str_passwordL;
//
//                            PutData putData = new PutData(url,"POST",field,data);
//                            if (putData.startPut()) {
//                                if (putData.onComplete()) {
//                                    String result = putData.getResult();
//                                    if (result.equals("Login Success")) {
//                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                                        startActivity(intent);
//                                        overridePendingTransition(0,0);
//                                        finish();
//                                    }
//                                    else{
//                                        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        }
//                    });
//
//                } else{
//                    Toast.makeText(getApplicationContext(), "All Field Required", Toast.LENGTH_SHORT).show();
//                }

        });

//        BottomNavigationView nav = findViewById(R.id.buttom_nav);
//        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.home_bar:
//                        Intent intent = new Intent(AccountLogin.this,MainActivity.class);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        finish();
//                        break;
//                    case R.id.favourite_bar:
//                        Intent intent1 = new Intent(AccountLogin.this,FavouriteFilm.class);
//                        startActivity(intent1);
//                        overridePendingTransition(0,0);
//                        finish();
//                        break;
//                }
//                return true;
//            }
//        });
    }
    }

