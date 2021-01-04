package gui.controller;

import javafx.event.ActionEvent;

import java.awt.*;
import java.net.URL;

public class MainController {
    public void addMovie(ActionEvent actionEvent) {
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
