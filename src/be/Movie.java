package be;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String year;
    private String filePath;
    private String lastView;
    private List<Category> categories;

    private int rating = 0;

    private int id;

    public Movie(String title, int id, String year, Category category,String filePath) {
        this.title = title;
        this.id = id;
        this.year = year;
        this.filePath = filePath;

        categories = new ArrayList<>();
        categories.add(category);
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

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        //must be more than 1 category in the list!
        if (categories.size() > 1) {
            categories.remove(category);
        }
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

}
