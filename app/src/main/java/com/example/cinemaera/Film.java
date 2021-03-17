package com.example.cinemaera;

import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

class Film {
    private String id;
    private String film_image;
    private String Price;
    private String film_name;
    private String trailer_videos;
    private String cast;
    private  String director;
    private String date;
    private String time;
    private String language;
    private String overview;

    public Film(String id, String film_image, String film_name, String Price, String trailer_videos, String cast, String director, String date, String time, String language, String overview) {
        this.id = id;
        this.film_image = film_image;
        this.Price = Price;
        this.film_name = film_name;
        this.trailer_videos = trailer_videos;
        this.cast = cast;
        this.director = director;
        this.date = date;
        this.time = time;
        this.language = language;
        this.overview = overview;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilm_image() {
        return film_image;
    }

    public void setFilm_image(String film_image) {
        this.film_image = film_image;
    }

    public String getPrice(){return Price;}

    public void setPrice(String Price){ this.Price = Price; }

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

    public static class FavouriteInfo {
        public String id, movies_id, A_name, Price, A_poster, Trailer_videos, Cast, Director, Release_date, Run_time, Language, Overview;

        public FavouriteInfo(String id, String movies_id, String a_name, String Price, String a_poster, String trailer_videos, String cast, String director, String release_date, String run_time, String language, String overview) {
            this.id = id;
            this.movies_id = movies_id;
            this.A_name = a_name;
            this.Price = Price;
            this.A_poster = a_poster;
            this.Trailer_videos = trailer_videos;
            this.Cast = cast;
            this.Director = director;
            this.Release_date = release_date;
            this.Run_time = run_time;
            this.Language = language;
            this.Overview = overview;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMovies_id(){
            return movies_id;
        }

        public  void setMovies_id(String movies_id){
            this.movies_id = movies_id;
        }

        public String getA_name() {
            return A_name;
        }

        public void setA_name(String a_name) {
            A_name = a_name;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getA_poster() {
            return A_poster;
        }

        public void setA_poster(String a_poster) {
            A_poster = a_poster;
        }

        public String getTrailer_videos() {
            return Trailer_videos;
        }

        public void setTrailer_videos(String trailer_videos) {
            Trailer_videos = trailer_videos;
        }

        public String getCast() {
            return Cast;
        }

        public void setCast(String cast) {
            Cast = cast;
        }

        public String getDirector() {
            return Director;
        }

        public void setDirector(String director) {
            Director = director;
        }

        public String getRelease_date() {
            return Release_date;
        }

        public void setRelease_date(String release_date) {
            Release_date = release_date;
        }

        public String getRun_time() {
            return Run_time;
        }

        public void setRun_time(String run_time) {
            Run_time = run_time;
        }

        public String getLanguage() {
            return Language;
        }

        public void setLanguage(String language) {
            Language = language;
        }

        public String getOverview() {
            return Overview;
        }

        public void setOverview(String overview) {
            Overview = overview;
        }
    }

    public static class ReviewInfo {
        public String reviews, RatedValue, userName, RatingAvg;

        public ReviewInfo(  String reviews, String RatedValue, String userName) {
            this.reviews = reviews;
            this.RatedValue = RatedValue;
            this.userName = userName;
        }
        public ReviewInfo(  String RatingAvg) {
            this.RatingAvg = RatingAvg;
        }

        public String getRatingAvg() {
            return RatingAvg;
        }

        public void setRatingAvg(String ratingAvg) {
            RatingAvg = ratingAvg;
        }

        public String getReviews() {
            return reviews;
        }

        public void setReviews(String reviews) {
            this.reviews = reviews;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRatedValue() {
            return RatedValue;
        }

        public void setRatedValue(String ratedValue) {
            RatedValue = ratedValue;
        }
    }
    public static class Search{
    String film_name;
        public Search(String film_name) {
            this.film_name = film_name;

        }

        public String getFilm_name() {
            return film_name;
        }

        public void setFilm_name(String film_name) {
            this.film_name = film_name;
        }
    }
}