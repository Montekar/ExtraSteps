package bll;

import be.Movie;
import dal.DAO.MovieDAO;

import java.util.List;

public class MovieManager {
    private MovieDAO movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO();
        loadAllCategories();
    }

    public void loadAllCategories() {
    }

    public void addMovie(String movieTitle, int movieYear, String filePath) {
        movieDAO.addMovie(movieTitle, movieYear, filePath);
    }

    public void editMovie(Movie movie) {
        movieDAO.editMovie(movie);
    }

    public void deleteMovie(int id) {
        movieDAO.deleteMovie(id);
    }

    public List<Movie> getMovies() {
        return movieDAO.getMovies();
    }
}
