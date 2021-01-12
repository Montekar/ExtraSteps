package gui.controller;

import be.Category;
import gui.model.CategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class Add {

    public static String[] addMovie(String title, String message) {
        MainController mainController = new MainController();
        CategoryModel categoryModel = new CategoryModel();
        String[] movie = new String[4];
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
        Label label3 = new Label("Pick the movie categories");
        ListView<Category> listView = new ListView<>();
        Label label5 = new Label("Select the movie location");
        TextField movieFile = new TextField();
        Button btnSelection = new Button("Find Location");
        movieFile.setEditable(false);

        listView.getItems().addAll(categoryModel.getObservableCategoryList());
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        Button add = new Button("Add movie");

        add.setOnAction(e -> {
            if (!movieTitle.getText().isEmpty() && !movieYear.getText().isEmpty() && !movieFile.getText().isEmpty()) {
                listView.getSelectionModel().getSelectedItems();
                System.out.println(listView.getSelectionModel().getSelectedItems());
                mainController.addCatMovie(listView.getSelectionModel().getSelectedItems());
                movie[0] = movieTitle.getText();
                movie[1] = movieYear.getText();
                movie[2] = movieFile.getText();
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
                if(path.endsWith(".mp4")||path.endsWith(".mpeg4")) {
                    movieFile.setText(path);
                }
            }
        });

        HBox movieFileLayout = new HBox();
        movieFileLayout.getChildren().addAll(movieFile,btnSelection);
        movieFileLayout.setAlignment(Pos.CENTER);
        movieFileLayout.setSpacing(15);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, movieTitle, label2,label3, listView, movieYear, label5, movieFileLayout, add);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return movie;
    }

    public static String[] addCategory(String title, String message) {
        String[] category = new String[1];
        Stage window = new Stage();
        //blocking other windows usage if this window is open
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);

        Label label = new Label();
        label.setText(message);

        Label label1 = new Label("Enter the new Category");
        TextField categoryName = new TextField();

        Button add = new Button("Add Category");

        add.setOnAction(e -> {
            if (!categoryName.getText().isEmpty()) {
                category[0] = categoryName.getText();
                window.close();
            } else {
                gui.controller.Alert.displayAlert("Alert", "You need to fill all the fields to add new category!!!");
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, categoryName, add);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return category;
    }
}
