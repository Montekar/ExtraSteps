package gui.controller;

import gui.model.MovieModel;
import javafx.event.ActionEvent;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;

public class MainController {

    MovieModel movieModel;

    public MainController() {
        movieModel = new MovieModel();
    }

    public void addMovie(ActionEvent actionEvent) {
        String[] result = AddMovie.display("Add Movie", "Fill the fields to add new Movie");

        boolean canAdd = Arrays.stream(result).anyMatch(a -> a != null && !a.isEmpty());

        //if (canAdd) movieModel.addMovie(result[0], result[1], result[2], result[3], result[4]);
    }

    public void editMovie(ActionEvent actionEvent) {
    }

    public void deleteMovie(ActionEvent actionEvent) {
    }

    public void addCategory(ActionEvent actionEvent) {
    }

    public void editCategory(ActionEvent actionEvent) {
    }

    public void deleteCategory(ActionEvent actionEvent) {
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
