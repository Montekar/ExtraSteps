package gui.controller;

import be.Category;
import be.Movie;
import gui.model.CategoryModel;
import gui.model.MovieModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.application.Platform;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    TableView<Category> categoryTable;
    @FXML
    private ChoiceBox<Category> choiceCategory;

    @FXML
    private Rating movieRating;

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
        choiceCategory.setItems(categoryModel.getObservableCategoryList());
        choiceCategory.getSelectionModel().selectFirst();
        movieModel.setCategoryID(choiceCategory.getValue().getId());
        choiceCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, category, t1) -> {
            movieModel.setCategoryID(choiceCategory.getValue().getId());
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
                double rating = movieTable.getSelectionModel().getSelectedItem().getRating();
                int index = movieTable.getSelectionModel().getSelectedIndex();
                movieRating.setRating(rating);
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
    //TODO: finish adding categories to the specific film (tip: adjust add movie that will also send the list of categories and based on the id it will
    // create neccesary links to catMovie)
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
        }
    }

    /*
    Method to edit a Category
     */
    public void editCategory(ActionEvent actionEvent) {
        if (categoryTable.getSelectionModel().getSelectedItem() != null) {
            Category category = categoryTable.getSelectionModel().getSelectedItem();
            String[] result = Edit.editCategory("Edit Movie", "Edit the chosen Movie", String.valueOf(category));

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

    /*
    Opens a MediaPlayer and play's movie
     */
    public void playMovie(ActionEvent actionEvent) {
        String moviePath = null;
        if (movieTable.getSelectionModel().getSelectedItem() != null) {
            //assigns the file path
            moviePath = movieTable.getSelectionModel().getSelectedItem().getFilePath();
            //check if the file path exists
            File file = new File( moviePath );
            boolean exists = file.canExecute();
            if (exists) {
                Runtime runtime = Runtime.getRuntime();
                try {
                    System.out.println(moviePath);
                    //adjust to personal media player to make it work
                    String[] command = {"C:\\Program Files (x86)\\Windows Media Player\\wmplayer", "" + moviePath + ""};
                    runtime.exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert.displayAlert("Movie not found", "Selected movie was not found. Check your file path.");
            }
        }


    }

}

