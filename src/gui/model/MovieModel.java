package gui.model;

import bll.MovieManager;

public class MovieModel {

    MovieManager movieManager = new MovieManager();

    public void addMovie(String s, String s1, String s2, String s3, String s4) {
        movieManager.addMovie(s, s1, s2, s3,s4);
    }
}
