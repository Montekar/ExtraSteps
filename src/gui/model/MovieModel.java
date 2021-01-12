package gui.model;

import be.Movie;
import bll.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MovieModel {

    public String getFilePath;
    private MovieManager movieManager;
    private ObservableList<Movie> moviesOverview;
    private int selectedCategoryID;

    public MovieModel() {
        movieManager = new MovieManager();
        moviesOverview = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void addMovie(String movieTitle, int movieYear, String filePath) {
        movieManager.addMovie(movieTitle, movieYear, filePath);
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

    public void setRating(int newRating, int id){
        movieManager.setRating(newRating, id);
        updateObservableList();
    }

    public void updateLastView(int movieID){
        movieManager.updateLastView(movieID);
        updateObservableList();
    }

    public void setCategoryID(int selectedCategoryID) {
        this.selectedCategoryID = selectedCategoryID;
        updateObservableList();
    }

    public void updateObservableList() {
        moviesOverview.clear();
        moviesOverview.addAll(movieManager.getCatMovies(selectedCategoryID));
    }

    public ObservableList<Movie> getObservableMovieList() {
        return moviesOverview;
    }
}
