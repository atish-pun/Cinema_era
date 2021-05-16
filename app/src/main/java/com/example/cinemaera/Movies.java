package com.example.cinemaera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
//        VideoLinearView = findViewById(R.id.VideoLinearView);
        videoConstrant = findViewById(R.id.videoConstrant);
        FullScreen = findViewById(R.id.Fullscreen);
        movies_path = getIntent().getStringExtra("Full_movie");
        film_name = getIntent().getStringExtra("Film names");
        movies_video.setVideoURI(Uri.parse(movies_path));
        getSupportActionBar().setTitle(film_name);
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
        movies_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                MovieBarLoad.setVisibility(View.GONE);
                seekBar.setMax(movies_video.getDuration());

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
                
            }
        });
    }
        public void FullScreen(){
            FullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullscreen){
//                    FullScreen.setImageDrawable(ContextCompat.getDrawable(Movies.this,R.drawable.ic_email));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ConstraintLayout.LayoutParams params =(ConstraintLayout.LayoutParams)movies_video.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    movies_video.setLayoutParams(params);
                    fullscreen = false;
                }
                else {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    FullScreen.setImageDrawable(ContextCompat.getDrawable(Movies.this,R.drawable.ic_back));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    ConstraintLayout.LayoutParams params =(ConstraintLayout.LayoutParams)movies_video.getLayoutParams();
                    params.width = params.WRAP_CONTENT;
                    params.height = params.WRAP_CONTENT;
                    movies_video.setLayoutParams(params);
                    fullscreen = true;
                }
            }
        });
    }
}