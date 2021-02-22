package com.example.cinemaera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileUser extends AppCompatActivity {
    TextView emailText, nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        emailText = findViewById(R.id.email);
        nameText = findViewById(R.id.name);

        emailText.setText(Util.GetValue(getApplicationContext(), "Cinemapref_name"));
        nameText.setText(Util.GetValue(getApplicationContext(), "Cinemapref_email"));

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