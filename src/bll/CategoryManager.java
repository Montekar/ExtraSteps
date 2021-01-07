package bll;

import be.Category;
import dal.DAO.CategoryDAO;

import java.util.List;

public class CategoryManager {
    private CategoryDAO categoryDAO;

    public CategoryManager() {
        categoryDAO = new CategoryDAO();
    }

    public void addCategory(String name) {
        categoryDAO.addCategory(name);
    }

    public void editCategory(Category category) {
        categoryDAO.editCategory(category);
    }

    public void deleteCategory(int id) {
        categoryDAO.deleteCategory(id);
    }

    public List<Category> getCategories() {
        return categoryDAO.getCategories();
    }
}
