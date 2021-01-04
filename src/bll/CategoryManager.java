package bll;

import be.Category;
import dal.DAO.CategoryDAO;
import dal.IDALFacade;

public class CategoryManager {
    private CategoryDAO categoryDAO;

    public void addCategory(String name) {
        categoryDAO.addCategory(name);
    }

    public void editCategory(Category category) {
        categoryDAO.editCategory(category);
    }

    public void deleteCategory(int id) {
        categoryDAO.deleteCategory(id);
    }
}
