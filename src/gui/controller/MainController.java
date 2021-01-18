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
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.w3c.dom.Text;

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
    private CheckBox checkAllMovies;

    @FXML
    private Rating movieRating;

    public final MovieModel movieModel;
    private final CategoryModel categoryModel;
    private static MoviePlayerManager moviePlayerManager;

    public MainController() {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
        moviePlayerManager = new MoviePlayerManager();
    }

    private boolean movieFilter(Movie movie) {
        if (movie.getTitle().toLowerCase().contains(searchBar.getText()) || String.valueOf(movie.getYear()).contains(searchBar.getText()) || String.valueOf(movie.getRating()).contains(searchBar.getText())) {
            if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
                if (choiceRating.getSelectionModel().getSelectedItem() == "ALL" || movie.getRating() >= Integer.parseInt(choiceRating.getSelectionModel().getSelectedItem())) {
                    int categoryID = choiceCategory.getSelectionModel().getSelectedItem().getId();
                    if (checkAllMovies.isSelected() || movie.getCategories().stream().anyMatch(category1 -> category1.getId() == categoryID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMovieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMovieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMovieRating.setCellValueFactory(rating -> rating.getValue().getRatingProperty());
        colMovieLastView.setCellValueFactory(movie -> movie.getValue().getLastViewProperty());
        movieTable.setItems(movieModel.getObservableMovieList());
        choiceCategory.setItems(categoryModel.getObservableCategoryList());
        choiceCategory.getSelectionModel().selectFirst();
        choiceRating.getItems().add("ALL");
        for (int i = 1; i <= 10; i++) {
            choiceRating.getItems().add(String.valueOf(i));
        }
        choiceRating.getSelectionModel().selectFirst();

        FilteredList<Movie> filteredData = new FilteredList<>(movieModel.getObservableMovieList(), p -> true);
        filteredData.setPredicate(this::movieFilter);
        checkAllMovies.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            filteredData.setPredicate(this::movieFilter);
        });
        searchBar.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(this::movieFilter);
        }));
        choiceCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, category, t1) -> {
            filteredData.setPredicate(this::movieFilter);
        });
        choiceRating.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            filteredData.setPredicate(this::movieFilter);
        });
        //the filtered data is put inside of the sorted list
        SortedList<Movie> sortedData = new SortedList<>(filteredData);

        //sorted list is bind with the table
        sortedData.comparatorProperty().bind(movieTable.comparatorProperty());

        //displaying the data
        movieTable.setItems(sortedData);


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
        checkAllMovies.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                choiceCategory.setDisable(checkAllMovies.isSelected());
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
        if (catName != null) {
            categoryModel.addCategory(catName);
            choiceCategory.getSelectionModel().selectLast();
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

            }
        }
    }

    /*
    Method to delete a Category
     */
    public void deleteCategory(ActionEvent actionEvent) {
        if (choiceCategory.getSelectionModel().getSelectedItem() != null) {
            int selectedId = choiceCategory.getSelectionModel().getSelectedItem().getId();
            if (movieModel.getCatMovies(selectedId).stream().anyMatch(movie -> movie.getCategories().size() <= 1)) {
                Alert.displayAlert("Error", "You can not delete a category that has movies with only 1 category!");
            } else {
                categoryModel.deleteCategory(selectedId);
                movieModel.updateAllMovies();
                choiceCategory.getSelectionModel().selectFirst();
            }
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

    public void topMovies(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.imdb.com/chart/top/?ref_=nv_mv_250").toURI());
        } catch (IOException | URISyntaxException ioException) {
            ioException.printStackTrace();
        }
    }

    public void topSeries(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.imdb.com/chart/toptv/?ref_=nv_tvv_250").toURI());
        } catch (IOException | URISyntaxException ioException) {
            ioException.printStackTrace();
        }
    }

    public void imdbPicks(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.imdb.com/imdbpicks/?ref_=nv_pi").toURI());
        } catch (IOException | URISyntaxException ioException) {
            ioException.printStackTrace();
        }
    }

}

