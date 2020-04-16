package sql;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component("databaseInst")
public class DatabaseInstance {
    private Connection connection;
    //prod
    private final String connectionStr="jdbc:mysql://151.248.112.108/honor-admin?useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
    private final String user="honor";
    private final String password="b4uld103";
    //dev
//    private final String connectionStr="jdbc:mysql:/ensler.ru/honor?useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
//    private final String user="trelloiii";
//    private final String password="b4uld103";
    public DatabaseInstance() throws SQLException {
        this.connection= DriverManager.getConnection(connectionStr,user, password);
    }
    public void reconnect() throws SQLException{
        this.connection=null;
        this.connection= DriverManager.getConnection(connectionStr,user, password);
    }
    public Connection getConnection() {
        return connection;
    }
}
