package edu.bbte.idde.feim1911.spring.dao.jdbc;

import edu.bbte.idde.feim1911.spring.RepositoryException;
import edu.bbte.idde.feim1911.spring.dao.ReviewDao;
import edu.bbte.idde.feim1911.spring.model.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@Profile({"jdbc"})
@Slf4j
public class ReviewJdbcDao implements ReviewDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public Review save(Review entity) {
        String query = "INSERT INTO idde.review VALUES (default, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setInt(2, entity.getStars());
            // preparedStatement.setLong(3, entity.getComponentId());

            preparedStatement.executeUpdate();

            log.info("New entity added.");
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));

                return entity;
            }

        } catch (SQLException e) {
            log.error("Error in addEntity: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return null;
    }

    private Review fromResultEntity(ResultSet resultSet) {
        try {
            return new Review(
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    // resultSet.getLong(4),
                    null
            );
        } catch (SQLException e) {
            log.error("Error in getEntities: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
    }

    @Override
    public Collection<Review> findAll() {
        Collection<Review> result = new ArrayList<>();
        // String query = "SELECT Id, Description, Stars, ComponentId FROM idde.review;";
        String query = "SELECT Id, Description, Stars FROM idde.review;";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Review component = fromResultEntity(resultSet);

                component.setId(resultSet.getLong(1));
                result.add(component);
            }
        } catch (SQLException e) {
            log.error("Error in getEntities: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return result;
    }

    @Override
    public Review getById(Long id) {
        // String query = "SELECT Id, Description, Stars, ComponentId FROM idde.review WHERE Id = ?";
        String query = "SELECT Id, Description, Stars FROM idde.review WHERE Id = ?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,  id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Review component = fromResultEntity(resultSet);

                component.setId(resultSet.getLong(1));

                return component;
            }
        } catch (SQLException e) {
            log.error("Error in getEntites: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM idde.review WHERE Id = ?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            Integer row = preparedStatement.executeUpdate();

            log.info("Deleted {} row/rows", row);
        } catch (SQLException e) {
            log.error("Error in deleteEntity: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
    }

    @Override
    public Collection<Review> findByStars(Integer count) {
        Collection<Review> result = new ArrayList<>();
        // String query = "SELECT Id, Description, Stars, ComponentId FROM idde.review WHERE Stars = ?";
        String query = "SELECT Id, Description, Stars FROM idde.review WHERE Stars = ?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,  count);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Review component = fromResultEntity(resultSet);

                component.setId(resultSet.getLong(1));
                result.add(component);
            }
        } catch (SQLException e) {
            log.error("Error in getEntityById: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return result;
    }

    public Boolean updateEntity(Long id, Review entity) {
        // String query = "UPDATE idde.review SET Description=?, ComponentId=?, Stars=? WHERE Id=?";
        String query = "UPDATE idde.review SET ComponentId=?, Stars=? WHERE Id=?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setInt(2, entity.getStars());
            // preparedStatement.setLong(3, entity.getComponentId());
            preparedStatement.setLong(4, id);

            Integer row = preparedStatement.executeUpdate();

            log.info("Updated {} row/rows", row);
        } catch (SQLException e) {
            log.error("Error in updateEntity: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return true;
    }

    @Override
    public Collection<Review> findByComponentId(Long id) {
        Collection<Review> result = new ArrayList<>();
        String query = "SELECT Id, Description, Stars FROM idde.review WHERE ComponentId = ?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,  id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Review component = fromResultEntity(resultSet);

                component.setId(resultSet.getLong(1));
                result.add(component);
            }
        } catch (SQLException e) {
            log.error("Error in getEntityById: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return result;
    }
}
