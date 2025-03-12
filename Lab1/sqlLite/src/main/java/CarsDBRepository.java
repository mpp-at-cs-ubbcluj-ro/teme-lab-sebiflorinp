import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.interfaces.ECKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private final Connection dbConnection;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        JdbcUtils dbUtils = new JdbcUtils(props);
        dbConnection = dbUtils.getConnection();
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.trace("Se cauta masinile dupa manufacturer");
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement("select * from cars where manufacturer = ?")) {
            preparedStatement.setString(1, manufacturerN);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.trace("Se cauta masinile dupa an");
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement("select * from cars where year >= ? and year <= ?")) {
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.trace("Se adauga o masina");
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement("insert into cars (manufacturer, model, year) values (?,?,?)")) {
            preparedStatement.setString(1,elem.getManufacturer());
            preparedStatement.setString(2, elem.getModel());
            preparedStatement.setInt(3, elem.getYear());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} car", result);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceEntry();

    }

    @Override
    public void update(Integer integer, Car elem) {
        logger.trace("Se updateaza o masina dupa id");
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement("update cars set manufacturer=?, model=?, year=? where id=?")) {
            preparedStatement.setString(1, elem.getManufacturer());
            preparedStatement.setString(2, elem.getModel());
            preparedStatement.setInt(3, elem.getYear());
            preparedStatement.setInt(4, elem.getId());
            int result = preparedStatement.executeUpdate();
            logger.trace("Updated {} car", result);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll() {
        logger.trace("Se cauta toate masinile");
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement("select * from cars")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.traceExit(cars);
        return cars;
    }
}
