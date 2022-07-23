package edu.bbte.idde.feim1911.spring.dao.jdbc;

import edu.bbte.idde.feim1911.spring.RepositoryException;
import edu.bbte.idde.feim1911.spring.dao.HardwareComponentDao;
import edu.bbte.idde.feim1911.spring.model.HardwareComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Repository
@Profile({"jdbc"})
@Slf4j
public class HardwareComponentJdbcDao implements HardwareComponentDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public HardwareComponent save(HardwareComponent entity) {
        String query = "INSERT INTO idde.hardwarecomponent VALUES (default, ?, ?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getType());
            preparedStatement.setDate(3, new Date(entity.getAppearance().getTime()));
            preparedStatement.setFloat(4, entity.getPrice());
            preparedStatement.setInt(5, entity.getInStock());

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

    private HardwareComponent fromResultEntity(ResultSet resultSet) {
        try {
            return new HardwareComponent(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getFloat(5),
                    resultSet.getInt(6),
                    resultSet.getTimestamp(7),
                    resultSet.getTimestamp(8),
                    Collections.emptyList()
            );
        } catch (SQLException e) {
            log.error("Error in getEntities: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
    }

    @Override
    public Collection<HardwareComponent> findAll() {
        Collection<HardwareComponent> result = new ArrayList<>();
        String query = "SELECT Id, Name, Type, Date, Price, InStock FROM idde.hardwarecomponent";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HardwareComponent component = fromResultEntity(resultSet);

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
    public HardwareComponent getById(Long id) {
        String query = "SELECT Id, Name, Type, Date, Price, InStock FROM idde.hardwarecomponent WHERE Id=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1,  id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                HardwareComponent component = fromResultEntity(resultSet);

                component.setId(resultSet.getLong(1));

                return  component;
            }
        } catch (SQLException e) {
            log.error("Error in getEntityById: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }

        return  null;
    }

    @Override
    public void deleteById(Long id) {
        String query = "DELETE FROM idde.hardwarecomponent WHERE Id = ?";
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
    public Collection<HardwareComponent> findByName(String name) {
        Collection<HardwareComponent> result = new ArrayList<>();
        String query = "SELECT Id, Name, Type, Date, Price, InStock FROM idde.hardwarecomponent WHERE Name = ?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,  name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HardwareComponent component = fromResultEntity(resultSet);

                component.setId(resultSet.getLong(1));
                result.add(component);
            }

        } catch (SQLException e) {
            log.error("Error in getEntityById: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return result;
    }

    @Override
    public int updateByLastViewedAt(Timestamp time, Long id) {
        return 0;
    }

    @Override
    public Collection<HardwareComponent> filterByLastUpdatedAt(Date date) {
        return Collections.emptyList();
    }

    public Boolean updateEntity(Long id, HardwareComponent entity) {
        String query = "UPDATE idde.hardwarecomponent SET Name = ?, Type=?, Date=?, Price=?, InStock=? WHERE Id = ?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getType());
            preparedStatement.setDate(3, new Date(entity.getAppearance().getTime()));
            preparedStatement.setFloat(4, entity.getPrice());
            preparedStatement.setInt(5, entity.getInStock());
            preparedStatement.setLong(6, id);

            Integer row = preparedStatement.executeUpdate();

            log.info("Updated {} row/rows", row);
        } catch (SQLException e) {
            log.error("Error in updateEntity: {} {}", e.getMessage(), e.getSQLState());
            throw new RepositoryException();
        }
        return true;
    }

}
