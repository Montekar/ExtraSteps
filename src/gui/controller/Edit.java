package gui.controller;

import be.Category;
import be.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;

public class Edit {

    public static Movie editMovie(Movie movie, ObservableList<Category> observableCategoryList) {
        AtomicReference<Movie> editedMovie = new AtomicReference<>();

        ObservableList<Category> movieCategories = FXCollections.observableArrayList(movie.getCategories());

        FilteredList<Category> filteredData = new FilteredList<>(observableCategoryList, p -> true);
        filteredData.setPredicate(category -> {
            return movieCategories.stream().noneMatch(c -> c.getId() == category.getId());
        });
        SortedList<Category> allCategores = new SortedList<>(filteredData);

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Edit Movie");

        Label label = new Label();
        label.setText("Edit Movie");

        //two buttons

        Label label1 = new Label("Update the movie title");
        TextField movieTitle = new TextField();
        movieTitle.setText(movie.getTitle());

        Label label2 = new Label("Update the movie year");
        TextField movieYear = new TextField();
        movieYear.setText(String.valueOf(movie.getYear()));

        HBox fileHbox = new HBox();
        TextField movieFile = new TextField();
        movieFile.setText(movie.getFilePath());
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

        Button update = new Button("Update Movie");

        update.setOnMouseClicked(e -> {
            if (movieTitle.getText().isEmpty()) {
                Alert.displayAlert("Invalid details", "Please enter title!");
            } else if (movieYear.getText().isEmpty()) {
                Alert.displayAlert("Invalid details", "Please enter year!");
            } else if (movieFile.getText().isEmpty()) {
                Alert.displayAlert("Invalid details", "Please add file location!");
            } else if(currentMovies.getItems().size()<1){
                Alert.displayAlert("Invalid details", "Please add at least 1 category!");
            }else{
                editedMovie.set(new Movie(movieTitle.getText(), movie.getId(), Integer.parseInt(movieYear.getText()), movieFile.getText(), movie.getRating()));
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

    public static Category editCategory(Category category) {
        AtomicReference<Category> tempCategory = new AtomicReference<>();

        Stage window = new Stage();

        //blocking other windows usage if this window is open
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Edit Category");

        Label label = new Label();
        label.setText("Edit Category");

        Label label1 = new Label("Update the category");
        TextField categoryName = new TextField();
        categoryName.setText(category.getName());

        Button update = new Button("Update");

        update.setOnAction(e -> {
            if(categoryName.getText().isEmpty()){
                Alert.displayAlert("Alert", "Please enter category name!");
            }
            else{
                tempCategory.set(new Category(categoryName.getText(),category.getId()));
                window.close();
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(label, label1, categoryName, update);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return tempCategory.get();

    }
}
