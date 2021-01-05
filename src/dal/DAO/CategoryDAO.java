package dal.DAO;

import be.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private List<Category> categories;

    public CategoryDAO() {
        categories = new ArrayList<>();
    }

    public void addCategory(String name) {
    }

    public void editCategory(Category category) {
    }

    public void deleteCategory(int id) {
    }

    public List<Category> getCategories() {
        return categories;
    }
}
