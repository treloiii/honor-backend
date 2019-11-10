package com.honor.back.honorwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

    ResultSet getResultSet(String query) throws SQLException {
        c=db.getConnection();
        PreparedStatement ps=c.prepareStatement(query);
        return ps.executeQuery();
    }

}
