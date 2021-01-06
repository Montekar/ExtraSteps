package bll;

import be.Category;
import be.Movie;
import dal.DAO.MovieDAO;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {
    private MovieDAO movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO();
    }

    public void addMovie(String movieTitle, String movieYear, String movieCategory, String filePath) {
        movieDAO.addMovie(movieTitle, movieYear, movieCategory, filePath);
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
