package be;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String year;
    private String filePath;
    private String lastView;
    private int rating;

    private int id;

    public Movie(String title, int id, String year, String filePath) {
        this.title = title;
        this.id = id;
        this.year = year;
        this.filePath = filePath;
        rating = 0;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setRating(int rating){
        if(rating>0 && rating<11){
            this.rating = rating;
        }
    }

    public void resetRating(){
        rating = 0;
    }

    public void setLastView(String date){
        lastView = date;
    }

    public String getLastView(){
        return lastView;
    }

}
