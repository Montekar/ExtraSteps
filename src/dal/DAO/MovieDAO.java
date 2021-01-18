package dal.DAO;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieDAO {
    private DBConnection connector;
    private List<Movie> movies;

    public MovieDAO() {
        connector = new DBConnection();

        updateAllMovies();
    }

    public void updateAllMovies() {
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
                    movie.setLastView(rs.getString("LastView"));
                    tempMovies.add(movie);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        movies = tempMovies;
    }

    public void updateMovie(int movieID) {
        try (Connection connection = connector.getConnection()) {
            String sql = "SELECT * FROM Movie WHERE MovieID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, movieID);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            if(rs.next()){
                Movie movie = new Movie(
                        rs.getString("Name"),
                        rs.getInt("MovieID"),
                        rs.getInt("Year"),
                        rs.getString("Link"),
                        rs.getInt("Rating"));
                movie.setLastView(rs.getString("LastView"));
                movie.setCategories(getCategories(movie.getId()));

                movies.stream().filter(f -> f.getId() == movieID).forEach(m -> {
                    m.setTitle(movie.getTitle());
                    m.setYear(movie.getYear());
                    m.setFilePath(movie.getFilePath());
                    m.setRating(movie.getRating());
                    m.setLastView(movie.getLastView());
                    m.setCategories(movie.getCategories());
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public List<Movie> getCatMovies(int categoryID) {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = connector.getConnection()) {
            String sql = "SELECT * FROM CatMovie INNER JOIN Movie ON CatMovie.MovieID = Movie.MovieID WHERE CategoryID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryID);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getString("Name"),
                        rs.getInt("MovieID"),
                        rs.getInt("Year"),
                        rs.getString("Link"),
                        rs.getInt("Rating"));
                movie.setLastView(rs.getString("LastView"));
                movie.setCategories(getCategories(movie.getId()));
                movies.add(movie);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movies;
    }
    public void addMovieCategory(int movieID, int categoryID) {
        try (Connection connection = connector.getConnection();) {
            String sql = "BEGIN IF NOT EXISTS(SELECT * FROM CatMovie WHERE CategoryID = ? AND MovieID = ?) BEGIN INSERT INTO CatMovie (CategoryID,MovieID) VALUES (?,?) END END";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryID);
            statement.setInt(2, movieID);
            statement.setInt(3, categoryID);
            statement.setInt(4, movieID);
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeMovieCategory(int movieID, int categoryID) {
        try (Connection connection = connector.getConnection();) {
            String sql = "DELETE FROM CatMovie WHERE CategoryID = ? AND MovieID = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryID);
            statement.setInt(2, movieID);
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addMovie(String movieTitle, int movieYear, String filePath,List<Category> categories) {
        try (Connection connection = connector.getConnection();) {
            String sql = "INSERT INTO Movie (Name,Year,Link) Values (?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movieTitle);
            statement.setInt(2, movieYear);
            statement.setString(3, filePath);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                Movie movie = new Movie(movieTitle, rs.getInt(1), movieYear, filePath, 0);
                for (Category category : categories) {
                    addMovieCategory(movie.getId(), category.getId());
                }
                movies.add(movie);
                updateMovie(movie.getId());
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

            List<Category> categoriesToRemove = getCategories(movie.getId()).stream().filter(category -> {
                return movie.getCategories().stream().noneMatch(c -> c.getId() == category.getId());
            }).collect(Collectors.toList());

            for (Category category : categoriesToRemove) {
                removeMovieCategory(movie.getId(), category.getId());
            }

            for (Category category : movie.getCategories()) {
                addMovieCategory(movie.getId(), category.getId());
            }
            updateMovie(movie.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateLastView(int movieID, String timeStamp) {
        try (Connection connection = connector.getConnection();) {
            String sql = "UPDATE Movie SET LastView = ? WHERE MovieID = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, timeStamp);
            statement.setInt(2, movieID);
            statement.execute();

            movies.stream().filter(m -> m.getId() == movieID).findFirst().ifPresent(value -> value.setLastView(timeStamp));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setRating(int newRating, int movieID) {
        if (newRating < 11 && newRating > 0) {
            try (Connection connection = connector.getConnection();) {
                String sql = "UPDATE Movie SET Rating = ? WHERE MovieID = ?;";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, newRating);
                statement.setInt(2, movieID);
                statement.execute();

                movies.stream().filter(m -> m.getId() == movieID).findFirst().ifPresent(value -> value.setRating(newRating));
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
