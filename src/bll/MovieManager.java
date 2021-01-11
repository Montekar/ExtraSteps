package bll;

import be.Movie;
import dal.DAO.MovieDAO;

import java.util.List;
import java.util.stream.Collectors;

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

    public void setRating(int newRating,int movieID){
        movieDAO.setRating(newRating, movieID);
    }

    public List<Movie> getMovies() {
        return movieDAO.getMovies();
    }

    public List<Movie> getCatMovies(int selectedCategoryID) {
        if(selectedCategoryID == 0){
            return getMovies();
        }
        return movieDAO.getMovies().stream().filter(x -> x.getCategories().stream().anyMatch(s -> s.getId() == selectedCategoryID)).collect(Collectors.toList());
    }
}
