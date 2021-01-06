package gui.controller;

import be.Category;
import be.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Edit {

    public static String[] editMovie(String title, String message, Movie movie) {
        String[] movieArr = new String[3];

        Stage window = new Stage();

        //blocking other windows usage if this window is open
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);

        Label label = new Label();
        label.setText(message);

        //two buttons

        Label label1 = new Label("Update the movie title");
        TextField movieTitle = new TextField();
        movieTitle.setText(movie.getName());

        Label label2 = new Label("Update the movie year");
        TextField movieYear = new TextField();
        movieYear.setText(movie.getYear());

        Button update = new Button("Update");

        update.setOnAction(e -> {

            if (!movieTitle.getText().isEmpty()  && !movieYear.getText().isEmpty()) {
                movieArr[0] = movieTitle.getText();
                movieArr[1] = movieYear.getText();
                window.close();
            } else {
                Alert.displayAlert("Alert", "Something went wrong try again!!!");
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, movieTitle, label2, movieYear, update);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return movieArr;

    }

    public static String[] editCategory(String title, String message, Category category) {
        String[] categoryArr = new String[1];

        Stage window = new Stage();

        //blocking other windows usage if this window is open
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);

        Label label = new Label();
        label.setText(message);

        Label label1 = new Label("Update the category");
        TextField categoryName = new TextField();
        categoryName.setText(category.getName());

        Button update = new Button("Update");

        update.setOnAction(e -> {

            if (!categoryName.getText().isEmpty()) {
                categoryArr[0] = categoryName.getText();
                window.close();
            } else {
                Alert.displayAlert("Alert", "Something went wrong try again!!!");
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, categoryName, update);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return categoryArr;

    }
}
