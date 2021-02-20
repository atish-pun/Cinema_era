package com.example.cinemaera;

import java.util.List;

class FilmCategoryName {
    int id;
    String categoryname;
    List<Film> films;

    public FilmCategoryName(int id, String categoryname, List<Film> films) {
        this.id = id;
        this.categoryname = categoryname;
        this.films = films;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
