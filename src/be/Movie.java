package be;

import javafx.beans.property.SimpleObjectProperty;

public class Movie {
    private int rating;
    private int year;

    private String title;
    private String filePath;
    private String lastView;

    private int id;

    public Movie(String title, int id, int year, String filePath,int rating) {
        this.title = title;
        this.id = id;
        this.year = year;
        this.filePath = filePath;
        this.rating = rating;
    }

    public Movie(String title, int id, int year, String filePath) {
        this.title = title;
        this.id = id;
        this.year = year;
        this.filePath = filePath;
        rating = 0;
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
    public SimpleObjectProperty<String> getRatingProperty(){
        if(rating==0){
            return new SimpleObjectProperty<>("no rating");
        }
        return new SimpleObjectProperty<>(String.valueOf(rating));
    }

    public String getTitle() {
        return title;
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

    @Override
    public String toString() {
        return "rating= " + rating+" " + "year= " + year+" " + "title= " + title+" " + "filePath= " + filePath+" " + "lastView= " + lastView+" " + "id= " + id+" " + "\n";
    }
}
