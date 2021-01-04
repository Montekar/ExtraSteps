package gui.model;

import be.Category;
import bll.CategoryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CategoryModel {

    private CategoryManager categoryManager;
    private ObservableList<Category> categoriesOverview;

    public CategoryModel() {
        categoryManager = new CategoryManager();
        categoriesOverview = FXCollections.observableArrayList(categoryManager.getCategories());        //get categories from manager
    }

    public void addCategory(String name) {
        categoryManager.addCategory(name);
        updateObservableList();
    }

    public void editCategory(Category category) {
        categoryManager.editCategory(category);
        updateObservableList();
    }

    public void deleteCategory(int id) {
        categoryManager.deleteCategory(id);
        updateObservableList();
    }

    public void updateObservableList() {
        categoriesOverview.clear();
        categoriesOverview.addAll(new ArrayList<>()); //get categories from manager
    }

    public ObservableList<Category> getObservableCategoryList() {
        return categoriesOverview;
    }

}