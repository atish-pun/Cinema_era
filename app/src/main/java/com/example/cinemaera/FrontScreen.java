package com.example.cinemaera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FrontScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_screen);
        getSupportActionBar().hide();
        ImageView img = findViewById(R.id.cinemasingle);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_animation);
        img.setAnimation(animation);
        ManageSession();

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(FrontScreen.this, AccountLogin.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void ManageSession(){
        String session = Util.GetValue(this,"Cinemapref_session");
        if(!session.equals("")){
            String User_id = Util.GetValue(this, "Cinemapref_uid");
            String Full_name = Util.GetValue(this, "Cinemapref_name");
            String Email_address = Util.GetValue(this, "Cinemapref_email");
            String FavoriteToken = Util.GetValue(this, "Cinemapref_favourite_token");

            Util.SESSION_KEY = session;
            Util.SESSION_USERID = User_id;
            Util.SESSION_NAME = Full_name;
            Util.SESSION_EMAIL = Email_address;
            Util.FAVOURITE_TOKEN = FavoriteToken;

            Thread thread = new Thread(){
                @Override
                public void run() {
                    try { sleep(1000);
                        Intent intent = new Intent(FrontScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        else {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try { sleep(2000);
                        Intent intent = new Intent(FrontScreen.this, AccountLogin.class);
                        startActivity(intent);
                        finish();
                    }
                    catch (Exception e){ e.printStackTrace(); }
                }
                };
            thread.start();
        }
        }
    }
