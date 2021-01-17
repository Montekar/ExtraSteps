package gui.controller;

import be.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reminder {
    public static void remindToDeleteOldAndBadRatedMovies(MainController mainController) {
        if (!badRatedOldMovies(mainController.movieModel.getAllMovies()).isEmpty()) {
            ObservableList<Movie> movies = FXCollections.observableArrayList(badRatedOldMovies(mainController.movieModel.getAllMovies()));

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Reminder");

            Label label = new Label();
            label.setText("List of Old or Bad Rated movies: ");

            ListView<Movie> movieListView = new ListView<>();
            movieListView.setItems(movies);

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);

            Label label1 = new Label("Selected Movie Actions: ");
            Button button = new Button("Delete");

            button.setOnMouseClicked(mouseEvent -> {
                if (movieListView.getSelectionModel().getSelectedItem() != null) {
                    Movie m = movieListView.getSelectionModel().getSelectedItem();
                    mainController.movieModel.deleteMovie(m.getId());
                    movies.clear();
                    movies.addAll(FXCollections.observableList(badRatedOldMovies(mainController.movieModel.getAllMovies())));
                }
            });

            hbox.getChildren().addAll(label1, button);

            VBox layout = new VBox(15);
            layout.setPadding(new Insets(15, 15, 15, 15));
            layout.getChildren().addAll(label, movieListView, hbox);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 300, 300);

            window.setScene(scene);

            window.setResizable(false);

            window.show();

        }
    }

    private static List<Movie> badRatedOldMovies(List<Movie> temp) {
        if (!temp.isEmpty()) {
            List<Movie> movies = new ArrayList<>();
            int currentYear = Year.now().getValue();
            for (Movie m : temp) {
                if (m.getRating() > 0 && m.getRating() <= 6) {
                    movies.add(m);
                } else if (m.getLastViewYear() != null) {
                    int lastViewYear = Integer.parseInt(m.getLastViewYear());
                    if (currentYear - lastViewYear >= 2) {
                        movies.add(m);
                    }
                }
            }
            if(!movies.isEmpty()){
                return movies;
            }
        }
        return new ArrayList<>();
    }
}
