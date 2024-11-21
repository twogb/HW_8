import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database database;
    private Connection connection;

    Database() throws SQLException {
        String dbUrl = "jdbc:h2:~/test;USER=sa";
        connection = DriverManager.getConnection(dbUrl);

    }

    public static Database getInstance() throws SQLException {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public Connection getConnection() {
        return connection;
    }

    public static String getSQLFromResource(String path) throws SQLException, URISyntaxException, IOException {
        String fileContent = null;

        String filePath = getFileFromResource(path); // Get file path from input argument
        // Read all lines into a List and then join them with line breaks to form a multiline String
        fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        return fileContent;

    }

    private static String getFileFromResource(String fileName) throws URISyntaxException {
        // Use the class loader to get the resource as a URL and convert it to a file path
        return Paths.get(ClassLoader.getSystemResource(fileName).toURI()).toString();


    }

}
