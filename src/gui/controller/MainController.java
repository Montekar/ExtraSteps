package gui.controller;

import be.Category;
import be.Movie;
import bll.MoviePlayerManager;
import gui.model.CategoryModel;
import gui.model.MovieModel;

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
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
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
    private TableColumn<Movie, String> colMovieLastView;

    @FXML
    private ChoiceBox<Category> choiceCategory;

    @FXML
    private ChoiceBox<String> choiceRating;

    @FXML
    private Rating movieRating;

    private final MovieModel movieModel;
    private final CategoryModel categoryModel;
    private static MoviePlayerManager moviePlayerManager;

    public MainController() {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
        moviePlayerManager = new MoviePlayerManager();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMovieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMovieRating.setCellValueFactory(rating -> rating.getValue().getRatingProperty());
        colMovieLastView.setCellValueFactory(movie -> movie.getValue().getLastViewProperty());

        choiceRating.getItems().add("1");
        choiceRating.getItems().add("2");
        choiceRating.getItems().add("3");
        choiceRating.getItems().add("4");
        choiceRating.getItems().add("5");
        choiceRating.getItems().add("6");
        choiceRating.getItems().add("7");
        choiceRating.getItems().add("8");
        choiceRating.getItems().add("9");
        choiceRating.getItems().add("10");

        movieTable.setItems(movieModel.getObservableMovieList());
        choiceCategory.setItems(categoryModel.getObservableCategoryList());
        choiceCategory.getSelectionModel().selectFirst();
        movieModel.setCategoryID(choiceCategory.getValue().getId());
        choiceCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, category, t1) -> {
            if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
                movieModel.setCategoryID(choiceCategory.getValue().getId());
            }
        });

        movieModel.setRatingID(choiceCategory.getValue().getId());
        choiceRating.getSelectionModel().selectedItemProperty().addListener((observableValue, category, t1) -> {
            if (choiceRating.getSelectionModel().getSelectedItem() != null) {
                int value = Integer.parseInt(choiceRating.getValue().toString());
                movieModel.setRatingID(value);
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

        choiceCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, category, t1) -> {
            if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
                movieModel.setCategoryID(choiceCategory.getValue().getId());
            }
        });

        movieTable.setOnMousePressed(mouseEvent -> {
            if (movieTable.getSelectionModel().getSelectedItem() != null) {
                int index = movieTable.getSelectionModel().getSelectedIndex();
                Movie movie = movieTable.getSelectionModel().getSelectedItem();
                double rating = movie.getRating();
                movieRating.setRating(rating);


                if (mouseEvent.getClickCount() == 2) {
                    if (moviePlayerManager.isWatchable(movie)) {
                        moviePlayerManager.playMovie(movie);
                        movieModel.updateLastView(movie.getId());
                    }
                }
                movieTable.getSelectionModel().select(index);
            }
        });
        movieRating.ratingProperty().addListener((observableValue, number, t1) -> {
            if (movieTable.getSelectionModel().getSelectedItem() != null) {
                int index = movieTable.getSelectionModel().getSelectedIndex();
                int rating = (int) movieRating.getRating();
                int movieID = movieTable.getSelectionModel().getSelectedItem().getId();
                movieModel.setRating(rating, movieID);
                movieTable.getSelectionModel().select(index);
            }
        });
    }

    /*
    Method to add a movie to the database
     */
    public void addMovie(ActionEvent actionEvent) {
        Movie tempMovie = Add.addMovie(categoryModel.getObservableCategoryList());
        if (tempMovie != null) {
            movieModel.addMovie(tempMovie.getTitle(), tempMovie.getYear(), tempMovie.getFilePath(), tempMovie.getCategories());
        }
    }

    /*
    Method to edit selected Movie
     */
    public void editMovie(ActionEvent actionEvent) {
        if (movieTable.getSelectionModel().getSelectedItem() != null) {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();

            Movie tempMovie = Edit.editMovie(movie, categoryModel.getObservableCategoryList());

            if (tempMovie != null) {
                movieModel.editMovie(tempMovie);
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
        String catName = Add.addCategory();
        if(catName!=null) {
            categoryModel.addCategory(catName);
            choiceCategory.getSelectionModel().selectFirst();
        }
    }

    /*
    Method to edit a Category
     */
    public void editCategory(ActionEvent actionEvent) {
        if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
            int index = choiceCategory.getSelectionModel().getSelectedIndex();
            Category category = choiceCategory.getSelectionModel().getSelectedItem();
            Category tempCategory = Edit.editCategory(category);
            if (tempCategory != null) {
                categoryModel.editCategory(tempCategory);
                movieModel.updateAllMovies();
                choiceCategory.getSelectionModel().select(index);
                movieModel.setCategoryID(tempCategory.getId());

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
            movieModel.updateAllMovies();
            choiceCategory.getSelectionModel().selectFirst();
        }
    }

    /*
     Opens imdb from our application
     */
    public void goTo(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.imdb.com/").toURI());
        } catch (IOException | URISyntaxException ioException) {
            ioException.printStackTrace();
        }
    }

}

