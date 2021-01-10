package bll;

import be.Movie;
import dal.DAO.MovieDAO;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {
    private MovieDAO movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO();
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

    public List<Movie> getCatMovies(int selectedCategoryID) {
        if(selectedCategoryID == 0){
            return getMovies();
        }
        List<Movie> categoryMovies = new ArrayList<>();
        for (Movie m : getMovies()){
            if(m.getCategories().stream().anyMatch(x -> x.getId() == selectedCategoryID)){
                categoryMovies.add(m);
            }
        }
            return categoryMovies;
    }
}
