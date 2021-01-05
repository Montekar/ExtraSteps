package gui.controller;

import be.Category;
import be.Movie;
import gui.model.CategoryModel;
import gui.model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;

public class MainController {

    @FXML
    TableView<Movie> movieTable;

    @FXML
    TableView<Category> categoryTable;

    MovieModel movieModel;
    CategoryModel categoryModel;

    public MainController() {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
    }

    /*
    Method to add a movie to the database
     */
    public void addMovie(ActionEvent actionEvent) {
        String[] result = Add.addMovie("Add Movie", "Fill the fields to add new Movie");
        if(Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
            movieModel.addMovie(result[0], result[1], result[2], result[3]);
        }
    }

    /*
    Method to edit selected Movie
     */
    public void editMovie(ActionEvent actionEvent) {
        if (movieTable.getSelectionModel().getSelectedItem() != null) {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();
            String[] result = Edit.editMovie("Edit Movie", "Edit the chosen Movie", movie);

            if(Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
                movie.setName(result[0]);
                movie.setYear(result[1]);
                movieModel.editMovie(movie);
            }
        }
    }

    /*
    Method to delete selected Movie
     */
    public void deleteMovie(ActionEvent actionEvent) {
        if(movieTable.getSelectionModel().getSelectedItem() != null){
            int selectedId = movieTable.getSelectionModel().getSelectedItem().getId();

            movieModel.deleteMovie(selectedId);

            movieTable.setItems(movieModel.getObservableMovieList());
        }
    }

    /*
    Method to add a Category
     */
    public void addCategory(ActionEvent actionEvent) {
        String[] result = Add.addCategory("Add Category", "Fill the fields to add new Category");
        if(Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
            categoryModel.addCategory(result[1]);
        }
    }

    /*
    Method to edit a Category
     */
    public void editCategory(ActionEvent actionEvent) {
        if (categoryTable.getSelectionModel().getSelectedItem() != null) {
            Category category = categoryTable.getSelectionModel().getSelectedItem();
            String[] result = Edit.editCategory("Edit Movie", "Edit the chosen Movie", category);

            if(Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
                category.setName(result[0]);
                categoryModel.editCategory(category);
            }
        }
    }

    /*
    Method to delete a Category
     */
    public void deleteCategory(ActionEvent actionEvent) {
        if(categoryTable.getSelectionModel().getSelectedItem() != null){
            int selectedId = categoryTable.getSelectionModel().getSelectedItem().getId();

            categoryModel.deleteCategory(selectedId);

            categoryTable.setItems(categoryModel.getObservableCategoryList());
        }
    }

    /*
     Opens imdb from our application
     */
    public void goTo(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.imdb.com/").toURI());
        } catch (Exception e) {}
    }
}
