package main;

import gui.controller.Alert;
import gui.controller.MainController;
import gui.controller.Reminder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("../gui/view/MainWindow.fxml")));
        MainController mainController = new MainController();
        fxmlLoader.setController(mainController);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Extra Steps");
        primaryStage.show();

        Reminder.remindToDeleteOldAndBadRatedMovies(mainController);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
