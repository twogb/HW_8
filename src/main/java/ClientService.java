import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Database db;

    public ClientService(Database db) throws SQLException {
        if (db == null) {
            throw new IllegalArgumentException("Database instance cannot be null.");
        }
        this.db = db;
    }

    public long create(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        long id;
        String sql = "INSERT INTO client (name) VALUES (?)";
        Connection conn = db.getConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                id = keys.getLong(1);
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public String getById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }

        String name = null;
        String sql = "SELECT NAME FROM client WHERE id = (?)";
        Connection conn = db.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();

            ResultSet set = statement.getResultSet();
            if (set.next()) {
                name = set.getString(1);
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    public void setName(long id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        String sql = "UPDATE clients SET name = ? WHERE id = ?";
        Connection connection = db.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setLong(2, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Client with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }

        String sql = "DELETE FROM clients WHERE id = ?";
        Connection connection = db.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Client with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Client> listAll() {

        String sql = "SELECT id, name FROM clients";
        Connection connection = db.getConnection();
        List<Client> clients = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                clients.add(new Client(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching clients", e);
        }

        return clients;
    }
}
