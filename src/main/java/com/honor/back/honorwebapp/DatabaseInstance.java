package com.honor.back.honorwebapp;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component("databaseInst")
public class DatabaseInstance {
    private Connection connection;

    public DatabaseInstance() throws SQLException {
        this.connection= DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_763?useLegacyDatetimeCode=false&serverTimezone=UTC","std_763", "b4uld103");
    }

    public Connection getConnection() {
        return connection;
    }
}
