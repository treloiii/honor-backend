package sql;

        import org.springframework.stereotype.Component;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

@Component("databaseInst")
public class DatabaseInstance {
    private Connection connection;

    public DatabaseInstance() throws SQLException {
        this.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1/honor?useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8","trelloiii", "b4uld103");
    }

    public Connection getConnection() {
        return connection;
    }
}
