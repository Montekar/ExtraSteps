package bll;

import be.Movie;
import gui.controller.Alert;

import java.io.File;
import java.io.IOException;

public class MoviePlayerManager {

    public boolean isWatchable(Movie movie) {
        String filePath = movie.getFilePath();
        File file = new File(filePath);

        if ((filePath.endsWith(".mp4") || filePath.endsWith(".mpeg4")) && file.canExecute()) {
            return true;
        }

        Alert.displayAlert("Movie not found", "Selected movie was not found. Check your file path.");
        return false;
    }

    public void playMovie(Movie movie) {
            try {
                String[] command = {"C:\\Program Files (x86)\\Windows Media Player\\wmplayer", "" + movie.getFilePath() + ""};
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
