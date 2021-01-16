package be;

import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String filePath;
    private String lastView;
    private int rating;
    private int year;
    private List<Category> categories;
    private int id;

    public Movie(String title, int id, int year, String filePath, int rating) {
        this.title = title;
        this.id = id;
        this.year = year;
        this.filePath = filePath;
        this.rating = rating;
        categories = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRating(int rating) {
        if (rating > 0 && rating < 11) {
            this.rating = rating;
        }
    }

    public void resetRating() {
        rating = 0;
    }

    public void setLastView(String date) {
        lastView = date;
    }

    public String getLastView() {
        return lastView;
    }

    public int getRating() {
        return rating;
    }

    public SimpleObjectProperty<String> getRatingProperty() {
        if (rating == 0) {
            return new SimpleObjectProperty<>("No rating");
        }
        return new SimpleObjectProperty<>(String.valueOf(rating) + " Stars");
    }

    public SimpleObjectProperty<String> getLastViewProperty() {
        if (lastView == "NULL" || lastView == null) {
            return new SimpleObjectProperty<>("Not watched");
        }
        return new SimpleObjectProperty<>(lastView);
    }

    public String getTitle() {
        return title;
    }

    public String getLastViewYear(){
        if(lastView!=null){
            String[] tempDate = getLastView().split("/");
            return tempDate[0];
        }
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "rating= " + rating + " " + "year= " + year + " " + "title= " + title + " " + "filePath= " + filePath + " " + "lastView= " + lastView + " " + "id= " + id + " " + "\n";
    }
}
