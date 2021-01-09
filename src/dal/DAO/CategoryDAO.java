package dal.DAO;

import be.Category;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private List<Category> categories;
    private DBConnection connector;

    public CategoryDAO() {
        connector = new DBConnection();
        categories = new ArrayList<>();
    }

    public void addCategory(String name) {
        try(Connection connection = connector.getConnection();) {
            String sql = "INSERT INTO Category (Name) Values (?);";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,name);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                Category category = new Category(name, rs.getInt(1));
                categories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editCategory(Category category) {
        try (Connection connection = connector.getConnection()) {
            String sql = "UPDATE Category SET Name = ? WHERE CategoryId = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,category.getName());
            statement.setInt(2,category.getId());
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        try (Connection connection = connector.getConnection()) {
            String sql = "DELETE FROM Movie WHERE Category = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.execute();
            categories.removeIf(category -> category.getId() == id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadAllCategories(){
    }

    public List<Category> getCategories() {
        return categories;
    }
}
