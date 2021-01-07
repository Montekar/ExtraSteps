package gui.controller;

import be.Category;
import be.Movie;
import gui.model.CategoryModel;
import gui.model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> colMovieTitle;
    @FXML
    private TableColumn<Movie, Integer> colMovieYear;
    @FXML
    private TableColumn<Movie, String> colMovieRating;


    @FXML
    TableView<Category> categoryTable;

    MovieModel movieModel;
    CategoryModel categoryModel;

    public MainController() {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMovieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMovieRating.setCellValueFactory(rating -> rating.getValue().getRatingProperty());

        movieTable.setItems(movieModel.getObservableMovieList());
    }

    /*
    Method to add a movie to the database
     */
    public void addMovie(ActionEvent actionEvent) {
        String[] result = Add.addMovie("Add Movie", "Fill the fields to add new Movie");
        if (Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
            movieModel.addMovie(result[0], Integer.parseInt(result[1]), result[2]);
        }
    }

    /*
    Method to edit selected Movie
     */
    public void editMovie(ActionEvent actionEvent) {
        if (movieTable.getSelectionModel().getSelectedItem() != null) {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();
            String[] result = Edit.editMovie("Edit Movie", "Edit the chosen Movie", movie);

            if (Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
                movie.setTitle(result[0]);
                movie.setYear(Integer.parseInt(result[1]));
                movieModel.editMovie(movie);
            }
        }
    }

    /*
    Method to delete selected Movie
     */
    public void deleteMovie(ActionEvent actionEvent) {
        if (movieTable.getSelectionModel().getSelectedItem() != null) {
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
        if (Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
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

            if (Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
                category.setName(result[0]);
                categoryModel.editCategory(category);
            }
        }
    }

    /*
    Method to delete a Category
     */
    public void deleteCategory(ActionEvent actionEvent) {
        if (categoryTable.getSelectionModel().getSelectedItem() != null) {
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
        } catch (Exception e) {
        }
    }

}
