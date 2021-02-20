package com.example.cinemaera;

import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

class Film {
    private String film_image ;
    private String film_name;
    private String trailer_videos;
    private String cast;
    private  String director;
    private String date;
    private String time;
    private String language;
    private String overview;

    public Film(String film_image, String film_name, String trailer_videos, String cast, String director, String date, String time, String language, String overview) {
        this.film_image = film_image;
        this.film_name = film_name;
        this.trailer_videos = trailer_videos;
        this.cast = cast;
        this.director = director;
        this.date = date;
        this.time = time;
        this.language = language;
        this.overview = overview;

    }

    public String getFilm_image() {
        return film_image;
    }

    public void setFilm_image(String film_image) {
        this.film_image = film_image;
    }

    public String getFilm_name() {
        return film_name;
    }

    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public String getTrailer_videos() {
        return trailer_videos;
    }

    public void setTrailer_videos(String trailer_videos) {
        this.trailer_videos = trailer_videos;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}