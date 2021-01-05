package dal.DAO;

import be.Movie;
import dal.DataBaseConnection.DbConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private DbConnectionProvider connector;

    public MovieDAO() {connector = new DbConnectionProvider();}


    private List<Movie> movies;

    public MovieDAO() {
        movies = new ArrayList<>();
    }

    public void addMovie(String movieTitle, String filePath) {
    }

    public void editMovie(Movie movie) {
    }

    public void deleteMovie(int id) {
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
