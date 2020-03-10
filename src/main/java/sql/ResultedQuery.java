package sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("resultedQuery")
public class ResultedQuery {
    Connection c;
    @Autowired
    DatabaseInstance db;
    public ResultedQuery() {
    }

    public ResultSet getResultSet(String query) throws SQLException {
        PreparedStatement ps;
        try {
            c = db.getConnection();
            ps = c.prepareStatement(query);
        }
        catch (Exception e){
            db.reconnect();
            ps = c.prepareStatement(query);
        }
        return ps.executeQuery();
    }
    public void VoidQuery(String query) throws SQLException{
        PreparedStatement ps;
        try {
            c = db.getConnection();
            ps = c.prepareStatement(query);
        }
        catch (Exception e){
            db.reconnect();
            ps = c.prepareStatement(query);
        }
        ps.execute();
    }

}
