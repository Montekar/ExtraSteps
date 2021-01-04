package be;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String filePath;
    private String lastView;
    private int rating = 0;

    private int id;

    private List<Category> categories;

    public Movie(String title, int id, Category category,String filePath) {
        this.title = title;
        this.id = id;
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
