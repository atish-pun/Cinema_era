package com.example.cinemaera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Movies extends AppCompatActivity {
    VideoView trailer_video;
    TextView  startTime, endTime;
    Button innerTrailerPlay;
    SeekBar seekBar;
    String Trailer_videos,film_name;
    LinearLayout VideoLinearView;
    ProgressBar MovieBarLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        trailer_video = findViewById(R.id.trailer_video);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        seekBar = findViewById(R.id.seekBar);
        MovieBarLoad = findViewById(R.id.MovieBarLoad);
        VideoLinearView = findViewById(R.id.VideoLinearView);
        Trailer_videos = getIntent().getStringExtra("Full_movie");
        film_name = getIntent().getStringExtra("Film names");
        trailer_video.setVideoURI(Uri.parse(Trailer_videos));
        setHandler();
        InitSeekBar();
        MoviesStart();
//        TrailerVideoCLick();
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
    public void MoviesStart(){
        trailer_video.start();
        trailer_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                MovieBarLoad.setVisibility(View.GONE);
                seekBar.setMax(trailer_video.getDuration());

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
//    public void innerTrialerplay(View view) {
//        if (trailer_video.isPlaying()) {
//            trailer_video.pause();
//            innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_play));
//        }
//        else {
//            trailer_video.start();
//            seekBar.setMax(trailer_video.getDuration());
//            innerTrailerPlay.setBackground(getDrawable(R.drawable.ic_pause));
//        }
//    }

    public void PausePlay(View view) {
        if(trailer_video.isPlaying()){
            trailer_video.pause();
        }
        else {
            trailer_video.start();
            seekBar.setMax(trailer_video.getDuration());
        }
    }

//    public void TrailerVideoCLick() {
//        trailer_video.setOnClickListener(new View.OnClickListener() {
//            boolean visible;
//
//            @Override
//            public void onClick(View v) {
//                visible = !visible;
//                TransitionManager.beginDelayedTransition(VideoLinearView);
//                innerTrailerPlay.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
//                endTime.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
//                startTime.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
//                seekBar.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
//            }
//        });
//    }
}