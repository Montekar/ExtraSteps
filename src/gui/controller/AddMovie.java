package gui.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class AddMovie {

    public static String[] display(String title, String message) {
        String[] movie = new String[5];
        Stage window = new Stage();
        //blocking other windows usage if this window is open
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);

        Label label = new Label();
        label.setText(message);

        //two buttons

        Label label1 = new Label("Enter the movie name");
        TextField movieTitle = new TextField();
        Label label2 = new Label("Enter the movie year");
        TextField movieYear = new TextField();
        Label label3 = new Label("Enter the movie category");
        TextField movieCategory = new TextField();
        Label label4 = new Label("Enter the movie rating");
        TextField movieRating = new TextField();
        Label label5 = new Label("Select the movie location");
        TextField movieFile = new TextField();
        Button btnSelection = new Button("Find Location");
        movieFile.setEditable(false);

        Button add = new Button("Add movie");

        add.setOnAction(e -> {
            if (!movieTitle.getText().isEmpty() && !movieRating.getText().isEmpty() && !movieCategory.getText().isEmpty() && !movieYear.getText().isEmpty() && !movieFile.getText().isEmpty()) {
                movie[0] = movieTitle.getText();
                movie[1] = movieYear.getText();
                movie[2] = movieCategory.getText();
                movie[3] = movieRating.getText();
                movie[4] = movieFile.getText();
                window.close();
            } else {
                gui.controller.Alert.displayAlert("Alert", "You need to fill all the fields to add new movie!!!");
            }
        });

        btnSelection.setOnAction(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if(response == fileChooser.APPROVE_OPTION){
                String path = fileChooser.getSelectedFile().getPath().replace('\\','/');
                movieFile.setText(path);
            }
        });

        HBox movieFileLayout = new HBox();
        movieFileLayout.getChildren().addAll(movieFile,btnSelection);
        movieFileLayout.setAlignment(Pos.CENTER);
        movieFileLayout.setSpacing(15);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, movieTitle, label2, movieYear, label3, movieCategory, label4, movieRating, label5, movieFileLayout, add);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return movie;
    }
}
