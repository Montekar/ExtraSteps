package dal.DAO;

import be.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private List<Movie> movies;

    public MovieDAO() {
        movies = new ArrayList<>();
    }

    public void addMovie(String movieTitle, String movieYear, String movieCategory, String filePath) {
    }

    public void editMovie(Movie movie) {
    }

    public void deleteMovie(int id) {
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
