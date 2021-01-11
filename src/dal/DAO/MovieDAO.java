package dal.DAO;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO {
    private DBConnection connector;
    private List<Movie> movies;

    public MovieDAO() {
        connector = new DBConnection();

        loadAllMovies();
    }

    public void loadAllMovies() {
        List<Movie> tempMovies = new ArrayList<>();
        try (Connection connection = connector.getConnection()) {
            String sql = "SELECT * FROM Movie";
            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getString("Name"),
                            rs.getInt("MovieID"),
                            rs.getInt("Year"),
                            rs.getString("Link"),
                            rs.getInt("Rating"));
                    movie.setCategories(getCategories(movie.getId()));
                    tempMovies.add(movie);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        movies = tempMovies;
    }

    public List<Category> getCategories(int movieID) {
        List<Category> movieCategories = new ArrayList<>();

        try (Connection connection = connector.getConnection()) {
            String sql = "SELECT * FROM CatMovie INNER JOIN Category ON Category.CategoryID=CatMovie.CategoryID WHERE MovieID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, movieID);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Category category = new Category(rs.getString("Name"),
                        rs.getInt("CategoryID"));
                movieCategories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movieCategories;
    }

    public void addMovie(String movieTitle, int movieYear, String filePath) {
        try (Connection connection = connector.getConnection();) {
            String sql = "INSERT INTO Movie (Name,Year,Link) Values (?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movieTitle);
            statement.setInt(2, movieYear);
            statement.setString(3, filePath);
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                Movie movie = new Movie(movieTitle, rs.getInt(1), movieYear, filePath, 0);
                movies.add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editMovie(Movie movie) {
        try (Connection connection = connector.getConnection();) {
            String sql = "UPDATE Movie SET Name = ?, Year = ?, Link = ? WHERE MovieID = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            statement.setString(3, movie.getFilePath());
            statement.setInt(4, movie.getId());
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setRating(int newRating, int movieID){
        if(newRating<11 && newRating>0) {
            try (Connection connection = connector.getConnection();) {
                String sql = "UPDATE Movie SET Rating = ? WHERE MovieID = ?;";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, newRating);
                statement.setInt(2,movieID);
                statement.execute();

                movies.stream().filter(m -> m.getId()== movieID).findFirst().ifPresent(value -> value.setRating(newRating));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void deleteMovie(int id) {
        try (Connection connection = connector.getConnection()) {
            String sql = "DELETE FROM Movie WHERE MovieID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            movies.removeIf(movie -> movie.getId() == id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
