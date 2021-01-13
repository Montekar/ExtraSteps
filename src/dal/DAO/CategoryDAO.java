package dal.DAO;

import be.Category;
import be.Movie;
import dal.DBConnection;

import java.sql.*;
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
    public void updateCategory(int categoryID) {
        try (Connection connection = connector.getConnection()) {
            String sql = "SELECT * FROM Category WHERE CategoryID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryID);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            if(rs.next()){
                Category category = new Category(rs.getString("Name"), categoryID);

                categories.stream().filter(f -> f.getId() == categoryID).forEach(c -> {
                    c.setName(category.getName());
                    c.setId(category.getId());
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addCategory(String name) {
        try (Connection connection = connector.getConnection();) {
            String sql = "INSERT INTO Category (Name) Values (?);";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                Category category = new Category(name, rs.getInt(1));
                categories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editCategory(Category category) {
            try (Connection connection = connector.getConnection();) {
                String sql = "UPDATE Category SET Name = ? WHERE CategoryID = ?;";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, category.getName());
                statement.setInt(2, category.getId());
                statement.execute();
                updateCategory(category.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public void deleteCategory(int id) {
            try (Connection connection = connector.getConnection()) {
                String sql = "DELETE FROM Category WHERE CategoryID = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.execute();
                categories.removeIf(category -> category.getId() == id);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public List<Category> getCategories() {
        return categories;
    }
}
