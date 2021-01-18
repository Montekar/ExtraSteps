package gui.model;

import be.Category;
import be.Movie;
import bll.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MovieModel {

    private MovieManager movieManager;
    private ObservableList<Movie> moviesOverview;

    public MovieModel() {
        movieManager = new MovieManager();
        moviesOverview = FXCollections.observableArrayList(movieManager.getMovies());
    }

    public void updateAllMovies(){
        movieManager.updateAllMovies();
        updateObservableList();
    }

    public void addMovie(String movieTitle, int movieYear, String filePath, List<Category> categories) {
        movieManager.addMovie(movieTitle, movieYear, filePath,categories);
        updateObservableList();
    }
    public void editMovie(Movie movie) {
        movieManager.editMovie(movie);
        updateObservableList();
    }

    public void deleteMovie(int id) {
        movieManager.deleteMovie(id);
        updateObservableList();
    }

    public List<Movie> getCatMovies(int catID){
        return movieManager.getCatMovies(catID);
    }

    public void setRating(int newRating, int id){
        movieManager.setRating(newRating, id);
        updateObservableList();
    }

    public void updateLastView(int movieID){
        movieManager.updateLastView(movieID);
        updateObservableList();
    }


    public List<Movie> getAllMovies(){
        return movieManager.getMovies();
    }

    public void updateObservableList() {
        moviesOverview.clear();
        moviesOverview.addAll(movieManager.getMovies());
    }

    public ObservableList<Movie> getObservableMovieList() {
        return moviesOverview;
    }
}
