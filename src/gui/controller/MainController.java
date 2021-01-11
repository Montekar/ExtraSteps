package gui.controller;

import be.Category;
import be.Movie;
import gui.model.CategoryModel;
import gui.model.MovieModel;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField searchBar;
    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> colMovieTitle;
    @FXML
    private TableColumn<Movie, Integer> colMovieYear;
    @FXML
    private TableColumn<Movie, String> colMovieRating;

    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private ChoiceBox<Category> choiceCategory;

    private final MovieModel movieModel;
    private final CategoryModel categoryModel;

    public MainController() {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMovieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMovieRating.setCellValueFactory(rating -> rating.getValue().getRatingProperty());

        choiceCategory.setItems(categoryModel.getObservableCategoryList());
        movieTable.setItems(movieModel.getObservableMovieList());
        choiceCategory.getSelectionModel().selectFirst();
        movieModel.setCategoryID(choiceCategory.getValue().getId());

        choiceCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, category, t1) -> {
            if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
                movieModel.setCategoryID(choiceCategory.getValue().getId());
            }
        });

        FilteredList<Movie> filteredData = new FilteredList<>(movieModel.getObservableMovieList(), p -> true);

        searchBar.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(movie -> {
                //if filter is empty display all data
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //value to lower case
                String newValLow = newValue.toLowerCase();

                //checks if the value is part of data in any movie
                if (movie.getTitle().toLowerCase().contains(newValLow)) {
                    return true;
                } else if (String.valueOf(movie.getYear()).contains(newValLow)) {
                    return true;
                } else if (String.valueOf(movie.getRating()).contains(newValLow)) {
                    return true;
                }
                return false;
            });

        }));

        //the filtered data is put inside of the sorted list
        SortedList<Movie> sortedData = new SortedList<>(filteredData);

        //sorted list is bind with the table
        sortedData.comparatorProperty().bind(movieTable.comparatorProperty());

        //displaying the data
        movieTable.setItems(sortedData);

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
        }
    }

    /*
    Method to add a Category
     */
    public void addCategory(ActionEvent actionEvent) {
        String[] result = Add.addCategory("Add Category", "Fill the fields to add new Category");
        if (Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
            categoryModel.addCategory(result[0]);
            choiceCategory.getSelectionModel().selectLast();
        }
    }

    /*
    Method to edit a Category
     */
    public void editCategory(ActionEvent actionEvent) {
        if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
            int index = choiceCategory.getSelectionModel().getSelectedIndex();
            String catName = choiceCategory.getSelectionModel().getSelectedItem().getName();
            int catID = choiceCategory.getSelectionModel().getSelectedItem().getId();

            String[] result = Edit.editCategory("Edit Movie", "Edit the chosen Movie", catName);

            if (Arrays.stream(result).anyMatch(e -> e != null && !e.isEmpty())) {
                Category category = new Category(result[0], catID);
                categoryModel.editCategory(category);
                choiceCategory.getSelectionModel().select(index);
            }
        }
    }

    /*
    Method to delete a Category
     */
    public void deleteCategory(ActionEvent actionEvent) {
        if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
            int selectedId = choiceCategory.getSelectionModel().getSelectedItem().getId();
            categoryModel.deleteCategory(selectedId);
            choiceCategory.getSelectionModel().selectFirst();
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
