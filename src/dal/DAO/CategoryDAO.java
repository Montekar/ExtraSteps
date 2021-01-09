package dal.DAO;

import be.Category;
import be.Movie;
import dal.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DBConnection connector;
    private List<Category> categories;

    public CategoryDAO() {
        connector = new DBConnection();

        loadAllCategories();
    }

    private void loadAllCategories() {
        List<Category> tempCategories = new ArrayList<>();

        Category categoryAllMovies = new Category("All",0);
        tempCategories.add(categoryAllMovies);

        try (Connection connection = connector.getConnection()) {
            String sql = "SELECT * FROM Category";
            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    Category category = new Category(
                            rs.getString("Name"),
                            rs.getInt("CategoryID"));

                    tempCategories.add(category);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        categories = tempCategories;
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
