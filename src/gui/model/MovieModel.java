package gui.model;

import be.Movie;
import bll.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovieModel {

    private MovieManager movieManager;
    private ObservableList<Movie> moviesOverview;

    public MovieModel() {
        movieManager = new MovieManager();
        moviesOverview = FXCollections.observableArrayList(movieManager.getMovies());
    }

    public void addMovie(String movieTitle, String filePath) {
        movieManager.addMovie(movieTitle,filePath);
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

    public void updateObservableList() {
        moviesOverview.clear();
        moviesOverview.addAll(movieManager.getMovies());
    }

    public ObservableList<Movie> getObservableMovieList() {
        return moviesOverview;
    }

}
