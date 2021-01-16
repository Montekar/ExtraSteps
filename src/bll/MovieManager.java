package bll;

import be.Category;
import be.Movie;
import dal.DAO.MovieDAO;
import gui.controller.Alert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class MovieManager {
    private MovieDAO movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO();
        loadAllCategories();
    }

    public void loadAllCategories() {
    }

    public void addMovie(String movieTitle, int movieYear, String filePath, List<Category> categories) {
        boolean noTitleMatch = getMovies().stream().noneMatch(o -> o.getTitle().toLowerCase().equals(movieTitle.toLowerCase()));
        if (noTitleMatch) {
            movieDAO.addMovie(movieTitle, movieYear, filePath,categories);
        } else {
            Alert.displayAlert("Adding Error", "Movie Could not be added! Please choose a different title!");
        }
    }

    public void editMovie(Movie movie) {
        movieDAO.editMovie(movie);
    }

    public void deleteMovie(int id) {
        movieDAO.deleteMovie(id);
    }

    public void setRating(int newRating, int movieID) {
        movieDAO.setRating(newRating, movieID);
    }

    public List<Movie> getMovies() {
        return movieDAO.getMovies();
    }

    public List<Movie> getCatMovies(int selectedCategoryID) {
        return movieDAO.getMovies().stream().filter(x -> x.getCategories().stream().anyMatch(s -> s.getId() == selectedCategoryID)).collect(Collectors.toList());
    }

    public void updateLastView(int movieID) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd - HH:mm").format(Calendar.getInstance().getTime());
        movieDAO.updateLastView(movieID, timeStamp);
    }

    public void updateAllMovies() {
        movieDAO.updateAllMovies();
    }

    public List<Movie> getRating(int selectedRatingID) {
        return  movieDAO.getMovies().stream().filter(movie -> movie.getRatingProperty().equals(selectedRatingID)).collect(Collectors.toList());
    }
}
