package gui.controller;

import be.Category;
import be.Movie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class Add {

    public static Movie addMovie(ObservableList<Category> observableCategoryList) {
        AtomicReference<Movie> editedMovie = new AtomicReference<>();

        ObservableList<Category> movieCategories = FXCollections.observableArrayList();

        FilteredList<Category> filteredData = new FilteredList<>(observableCategoryList, p -> true);
        filteredData.setPredicate(category -> {
            return movieCategories.stream().noneMatch(c -> c.getId() == category.getId());
        });
        SortedList<Category> allCategores = new SortedList<>(filteredData);

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add Movie");

        Label label = new Label();
        label.setText("Add Movie");

        //two buttons

        Label label1 = new Label("Movie title");
        TextField movieTitle = new TextField();

        Label label2 = new Label("Movie year");
        TextField movieYear = new TextField();

        movieYear.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    movieYear.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        HBox fileHbox = new HBox();
        TextField movieFile = new TextField();
        Button btnSelection = new Button("Find Location");
        movieFile.setEditable(false);
        fileHbox.getChildren().addAll(movieFile, btnSelection);
        fileHbox.setAlignment(Pos.CENTER);
        fileHbox.setPadding(new Insets(15, 15, 15, 15));
        fileHbox.setSpacing(15);
//category selection
        HBox hBox = new HBox();

        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.CENTER);
        Label allCategoryLabel = new Label("Available categories");
        ListView<Category> availableCategoryList = new ListView<>();
        availableCategoryList.setItems(allCategores);
        vbox1.getChildren().addAll(allCategoryLabel, availableCategoryList);

        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        Button addCategory = new Button();
        addCategory.setText("Add Category");

        addCategory.setOnMouseClicked(mouseEvent -> {
            if (availableCategoryList.getSelectionModel().getSelectedItem() != null) {
                movieCategories.add(availableCategoryList.getSelectionModel().getSelectedItem());

                filteredData.setPredicate(category -> {
                    return movieCategories.stream().noneMatch(c -> c.getId() == category.getId());
                });
            }
        });

        btnSelection.setOnAction(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if (response == fileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getPath().replace('\\', '/');
                if (path.endsWith(".mp4") || path.endsWith(".mpeg4")) {
                    movieFile.setText(path);
                }
            }
        });

        Button removeCategory = new Button();
        removeCategory.setText("Remove Category");

        vbox2.setPadding(new Insets(15, 15, 15, 15));
        vbox2.setSpacing(15);

        vbox2.getChildren().addAll(addCategory, removeCategory);


        VBox vBox3 = new VBox();
        vBox3.setAlignment(Pos.CENTER);
        Label currentCategoryLabel = new Label("Movie categories");
        ListView<Category> currentMovies = new ListView<>();
        currentMovies.setItems(movieCategories);

        removeCategory.setOnMouseClicked(mouseEvent -> {
            if (currentMovies.getSelectionModel().getSelectedItem() != null) {
                currentMovies.getItems().remove(currentMovies.getSelectionModel().getSelectedItem());

                filteredData.setPredicate(category -> {
                    return movieCategories.stream().noneMatch(c -> c.getId() == category.getId());
                });
            }
        });

        vBox3.getChildren().addAll(currentCategoryLabel, currentMovies);

        hBox.getChildren().addAll(vbox1, vbox2, vBox3);

        Button update = new Button("Add Movie");

        update.setOnMouseClicked(e -> {
            if (movieTitle.getText().isEmpty()) {
                gui.controller.Alert.displayAlert("Invalid details", "Please enter title!");
            } else if (movieYear.getText().isEmpty()) {
                gui.controller.Alert.displayAlert("Invalid details", "Please enter year!");
            } else if (movieFile.getText().isEmpty()) {
                gui.controller.Alert.displayAlert("Invalid details", "Please add file location!");
            } else if(currentMovies.getItems().size()<1){
                Alert.displayAlert("Invalid details", "Please add at least 1 category!");
            }else{
                editedMovie.set(new Movie(movieTitle.getText(), -1, Integer.parseInt(movieYear.getText()), movieFile.getText(), 0));
                editedMovie.get().setCategories(movieCategories);
                window.close();
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, movieTitle, label2, movieYear, fileHbox, hBox, update);
        layout.setAlignment(Pos.CENTER);


        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return editedMovie.get();
    }

    public static String addCategory() {
        AtomicReference<String> tempCategoryName = new AtomicReference<>();
        Stage window = new Stage();
        //blocking other windows usage if this window is open
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add Category");

        Label label = new Label();
        label.setText("Add Category");

        Label label1 = new Label("Enter the new Category");
        TextField categoryName = new TextField();

        Button add = new Button("Add Category");

        add.setOnMouseClicked(mouseEvent -> {
            if (categoryName.getText().trim().isBlank()) {
                Alert.displayAlert("Invalid details", "Please enter category name!");
            } else {
                tempCategoryName.set(categoryName.getText());
                window.close();
            }
        });
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, categoryName, add);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return tempCategoryName.get();
}
}