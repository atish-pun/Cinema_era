package com.example.cinemaera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.jetbrains.annotations.NotNull;

public class Movies extends AppCompatActivity {
    VideoView movies_video;
    TextView  startTime, endTime;
    Button innerTrailerPlay;
    SeekBar seekBar;
    Boolean fullscreen = false;
    String movies_path,film_name;
    LinearLayout VideoLinearView;
    ProgressBar MovieBarLoad;
    ImageView FullScreen;
    ConstraintLayout videoConstrant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movies_video = findViewById(R.id.movies_video);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        seekBar = findViewById(R.id.seekBar);
        innerTrailerPlay = findViewById(R.id.innerTrialerplay);
        MovieBarLoad = findViewById(R.id.MovieBarLoad);
        videoConstrant = findViewById(R.id.videoConstrant);
        FullScreen = findViewById(R.id.Fullscreen);
        movies_path = getIntent().getStringExtra("Full_movie");
        film_name = getIntent().getStringExtra("Film names");
        movies_video.setVideoURI(Uri.parse(movies_path));
        getSupportActionBar().setTitle(film_name);
        endTime.setVisibility(View.INVISIBLE);
        startTime.setVisibility(View.INVISIBLE);
        seekBar.setVisibility(View.INVISIBLE);
        innerTrailerPlay.setVisibility(View.INVISIBLE);
        FullScreen.setVisibility(View.INVISIBLE);
        setHandler();
        InitSeekBar();
        MoviesStart();
        TrailerVideoCLick();
        FullScreen();
    }

    public void setHandler(){
        final Handler moviehandler = new Handler();
        Runnable movierun = new Runnable() {
            @Override
            public void run() {
                if (movies_video.getCurrentPosition() > 0){
                    int currentTime = movies_video.getCurrentPosition();
                    seekBar.setProgress(currentTime);
                    startTime.setText(convertIntoTime(currentTime));
                    endTime.setText(convertIntoTime(movies_video.getDuration()));
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
                            movies_video.seekTo(progress);
                            int currentTime = movies_video.getCurrentPosition();
                            startTime.setText(convertIntoTime(currentTime));
                            endTime.setText(convertIntoTime(movies_video.getDuration()));
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
    public void MoviesStart(){
        movies_video.start();
        seekBar.setMax(movies_video.getDuration());
        movies_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                MovieBarLoad.setVisibility(View.GONE);
                seekBar.setMax(movies_video.getDuration());
                endTime.setVisibility(View.VISIBLE);
                startTime.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                innerTrailerPlay.setVisibility(View.VISIBLE);
                FullScreen.setVisibility(View.VISIBLE);

            }
        });
        movies_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_play));
            }
        });

        movies_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
          @Override
          public boolean onError(MediaPlayer mp, int what, int extra) {
              MovieBarLoad.setVisibility(View.GONE);
              AlertDialog.Builder alert = new AlertDialog.Builder(Movies.this,R.style.Alert);
              alert.setTitle("Can't play movie");
              alert.setMessage("Movie  of "+film_name+" isn't available!!");
              alert.setPositiveButton("OK",null);
              AlertDialog alertDialog = alert.create();
              alertDialog.setCanceledOnTouchOutside(false);
              alertDialog.show();
              return true;
          }
      });
    }

    public void PausePlay(View view) {
        if(movies_video.isPlaying()){
            movies_video.pause();
            innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_play));

        }
        else {
            movies_video.start();
            seekBar.setMax(movies_video.getDuration());
            innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_pause));

        }
    }

    public void TrailerVideoCLick() {
        movies_video.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View v) {
                visible = !visible;
                TransitionManager.beginDelayedTransition(videoConstrant);
                endTime.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                startTime.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                seekBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                innerTrailerPlay.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                FullScreen.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                
            }
        });
    }

    public void FullScreen(){
            FullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullscreen){
                    FullScreen.setImageDrawable(ContextCompat.getDrawable(Movies.this,R.drawable.ic_full_screen));
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) movies_video.getLayoutParams();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    movies_video.setLayoutParams(params);
                    fullscreen = false;
                }
                else {
                    FullScreen.setImageDrawable(ContextCompat.getDrawable(Movies.this,R.drawable.ic_full_screen_exit));
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) movies_video.getLayoutParams();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    movies_video.setLayoutParams(params);
                    fullscreen = true;
                }
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}