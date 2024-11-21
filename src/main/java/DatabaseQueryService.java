import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {

    private Database database;

    public static void main(String[] args) throws SQLException {
        Database database = Database.getInstance();
        DatabaseQueryService queryService = new DatabaseQueryService(database);
        System.out.println(queryService.findMaxProjectsClient());
        System.out.println(queryService.findYoungestEldestWorker());
        System.out.println(queryService.findMaxLongestProjects());
        System.out.println(queryService.findMaxSalaryWorkers());
    }

    public DatabaseQueryService(Database database) {
        this.database = database; // Ініціалізуємо об'єкт для підключення до бази даних
    }

    public List<MaxProjectCountClient> findMaxProjectsClient() {
        List<MaxProjectCountClient> clients = new ArrayList<>();
        String query = null;
        try {
            query = Database.getSQLFromResource("sql/find_max_projects_client.sql"); // Get file path from input argument
            Connection connection = Database.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                int amount = resultSet.getInt(2);
                MaxProjectCountClient maxProjectCountClient = new MaxProjectCountClient(name, amount);
                clients.add(maxProjectCountClient);
            }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    public List<YoungestEldestWorker> findYoungestEldestWorker() {
        List<YoungestEldestWorker> workers = new ArrayList<>();
        String youngestSQL = null;
        try {
            youngestSQL = Database.getSQLFromResource("sql/find_youngest_eldest_workers.sql");
            Connection connection = Database.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(youngestSQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String type = resultSet.getString(1);
                String name = resultSet.getString(2);
                String birthday = resultSet.getString(3);
                YoungestEldestWorker youngestEldestWorker = new YoungestEldestWorker(type, name, birthday);
                workers.add(youngestEldestWorker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return workers;
    }

    public List<Object> findMaxLongestProjects() {
        List<Object> project = new ArrayList<>();
        String query = null;
        try {
            query = Database.getSQLFromResource("sql/find_longest_project.sql"); // Get file path from input argument
            Connection connection = Database.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                Object longestProject = new LongestProject(name);
                project.add(longestProject);
            }
            preparedStatement.closeOnCompletion();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    public List<MaxSalaryWorkers> findMaxSalaryWorkers() {
        List<MaxSalaryWorkers> workers = new ArrayList<>();
        String query = null;
        try {
            query = Database.getSQLFromResource("sql/find_max_salary_worker.sql"); // Get file path from input argument
            Connection connection = Database.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                int salary = resultSet.getInt(2);
                MaxSalaryWorkers worker = new MaxSalaryWorkers(name, salary);
                workers.add(worker);
            }


        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return workers;
    }
}



