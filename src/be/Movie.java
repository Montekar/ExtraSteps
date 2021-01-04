package be;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String name;
    private int id;

    private List<Category> categories;

    public Movie(String name, int id, Category category) {
        this.name = name;
        this.id = id;

        categories = new ArrayList<>();
        categories.add(category);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
